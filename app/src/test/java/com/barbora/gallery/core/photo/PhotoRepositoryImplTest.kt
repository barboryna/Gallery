package com.barbora.gallery.core.photo

import com.barbora.gallery.core.database.FavoriteDao
import com.barbora.gallery.core.database.FavoriteEntity
import com.barbora.gallery.core.photo.network.PhotoResponse
import com.barbora.gallery.core.photo.network.RemotePhotoDataSource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class PhotoRepositoryImplTest {
    private val remoteDataSource: RemotePhotoDataSource = mockk()
    private val favoriteDao: FavoriteDao = mockk()
    private lateinit var repository: PhotoRepositoryImpl

    @Before
    fun setup() {
        repository = PhotoRepositoryImpl(remoteDataSource, favoriteDao)
    }

    private fun fakeResponse(id: String) = PhotoResponse(
        id = id,
        author = "Author",
        width = 800,
        height = 600,
        url = "https://picsum.photos/id/$id",
        downloadUrl = "https://picsum.photos/id/$id/800/600"
    )

    private fun fakeEntity(id: String) = FavoriteEntity(
        id = id,
        author = "Author",
        width = 800,
        height = 600,
        url = "https://picsum.photos/id/$id",
        downloadUrl = "https://picsum.photos/id/$id/800/600"
    )

    @Test
    fun getPhotos_marksCorrectPhotosAsFavorite() = runTest {
        coEvery { remoteDataSource.getPhotos(1, 30) } returns
            listOf(fakeResponse("1"), fakeResponse("2"), fakeResponse("3"))
        coEvery { favoriteDao.getAll() } returns listOf(fakeEntity("2"))

        val result = repository.getPhotos(1, 30)

        assertFalse(result.first { it.id == "1" }.isFavorite)
        assertTrue(result.first { it.id == "2" }.isFavorite)
        assertFalse(result.first { it.id == "3" }.isFavorite)
    }

    @Test
    fun getPhotos_allFalseWhenNoFavorites() = runTest {
        coEvery { remoteDataSource.getPhotos(1, 30) } returns
            listOf(fakeResponse("1"), fakeResponse("2"))
        coEvery { favoriteDao.getAll() } returns emptyList()

        val result = repository.getPhotos(1, 30)

        assertTrue(result.all { !it.isFavorite })
    }

    @Test
    fun getPhotos_returnsCorrectCount() = runTest {
        val responses = (1..5).map { fakeResponse(it.toString()) }
        coEvery { remoteDataSource.getPhotos(1, 5) } returns responses
        coEvery { favoriteDao.getAll() } returns emptyList()

        assertEquals(5, repository.getPhotos(1, 5).size)
    }

    @Test
    fun getPhoto_isFavoriteTrueWhenInDb() = runTest {
        coEvery { remoteDataSource.getPhoto("42") } returns fakeResponse("42")
        coEvery { favoriteDao.isFavorite("42") } returns true

        val result = repository.getPhoto("42")

        assertTrue(result.isFavorite)
        assertEquals("42", result.id)
    }

    @Test
    fun getPhoto_isFavoriteFalseWhenNotInDb() = runTest {
        coEvery { remoteDataSource.getPhoto("99") } returns fakeResponse("99")
        coEvery { favoriteDao.isFavorite("99") } returns false

        assertFalse(repository.getPhoto("99").isFavorite)
    }

    @Test
    fun getPhoto_queriesRemoteWithCorrectId() = runTest {
        coEvery { remoteDataSource.getPhoto("7") } returns fakeResponse("7")
        coEvery { favoriteDao.isFavorite("7") } returns false

        repository.getPhoto("7")

        coVerify(exactly = 1) { remoteDataSource.getPhoto("7") }
    }
}

