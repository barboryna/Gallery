package com.barbora.gallery.home.gallery

import com.barbora.gallery.core.photo.Photo
import com.barbora.gallery.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GalleryViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val getPhotosUseCase: GetPhotosUseCase = mockk()

    private fun fakePhoto(id: String = "1") = Photo(
        id = id,
        author = "Author",
        width = 800,
        height = 600,
        url = "https://example.com/$id",
        downloadUrl = "https://example.com/$id/download"
    )

    @Test
    fun `loadPhotos success - photoList is populated`() = runTest {
        val photos = listOf(fakePhoto("1"), fakePhoto("2"))
        coEvery { getPhotosUseCase(page = 1) } returns photos

        val viewModel = GalleryViewModel(getPhotosUseCase)
        advanceUntilIdle()

        assertEquals(photos, viewModel.photoList.value)
    }

    @Test
    fun `loadPhotos success - error is null`() = runTest {
        coEvery { getPhotosUseCase(page = 1) } returns emptyList()

        val viewModel = GalleryViewModel(getPhotosUseCase)
        advanceUntilIdle()

        assertNull(viewModel.error.value)
    }

    @Test
    fun `loadPhotos success - loading is false after completion`() = runTest {
        coEvery { getPhotosUseCase(page = 1) } returns emptyList()

        val viewModel = GalleryViewModel(getPhotosUseCase)
        advanceUntilIdle()

        assertFalse(viewModel.loading.value)
    }

    // Error path

    @Test
    fun `loadPhotos error - error message is set`() = runTest {
        coEvery { getPhotosUseCase(page = 1) } throws RuntimeException("network failure")

        val viewModel = GalleryViewModel(getPhotosUseCase)
        advanceUntilIdle()

        assertEquals("network failure", viewModel.error.value)
    }

    @Test
    fun `loadPhotos error - photoList stays empty`() = runTest {
        coEvery { getPhotosUseCase(page = 1) } throws RuntimeException("network failure")

        val viewModel = GalleryViewModel(getPhotosUseCase)
        advanceUntilIdle()

        assertTrue(viewModel.photoList.value.isEmpty())
    }

    @Test
    fun `loadPhotos error - loading is false after failure`() = runTest {
        coEvery { getPhotosUseCase(page = 1) } throws RuntimeException("network failure")

        val viewModel = GalleryViewModel(getPhotosUseCase)
        advanceUntilIdle()

        assertFalse(viewModel.loading.value)
    }

    // Retry

    @Test
    fun `onRetryClick after error - clears error and loads photos`() = runTest {
        val photos = listOf(fakePhoto("1"))
        coEvery { getPhotosUseCase(page = 1) }
            .throwsMany(listOf(RuntimeException("fail")))
            .andThen(photos)

        val viewModel = GalleryViewModel(getPhotosUseCase)
        advanceUntilIdle()
        assertEquals("fail", viewModel.error.value)

        viewModel.onRetryClick()
        advanceUntilIdle()

        assertNull(viewModel.error.value)
        assertEquals(photos, viewModel.photoList.value)
    }
}

