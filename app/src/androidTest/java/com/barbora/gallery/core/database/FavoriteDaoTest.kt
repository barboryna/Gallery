package com.barbora.gallery.core.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoriteDaoTest {
    private lateinit var db: GalleryDatabase
    private lateinit var dao: FavoriteDao

    @Before
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GalleryDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = db.favoriteDao()
    }

    @After
    fun teardown() {
        db.close()
    }

    private fun entity(id: String) = FavoriteEntity(
        id = id,
        author = "Author",
        width = 800,
        height = 600,
        url = "https://picsum.photos/id/$id",
        downloadUrl = "https://picsum.photos/id/$id/800/600"
    )

    @Test
    fun insertAndGetAll_returnsInsertedEntity() = runTest {
        dao.insert(entity("1"))

        val result = dao.getAll()

        assertEquals(1, result.size)
        assertEquals("1", result[0].id)
    }

    @Test
    fun isFavorite_trueForInsertedId() = runTest {
        dao.insert(entity("5"))

        assertTrue(dao.isFavorite("5"))
    }

    @Test
    fun isFavorite_falseForUnknownId() = runTest {
        assertFalse(dao.isFavorite("unknown"))
    }

    @Test
    fun delete_removesEntityFromDb() = runTest {
        val e = entity("3")
        dao.insert(e)
        dao.delete(e)

        assertTrue(dao.getAll().isEmpty())
        assertFalse(dao.isFavorite("3"))
    }

    @Test
    fun insert_replaceOnConflict_doesNotDuplicate() = runTest {
        dao.insert(entity("7"))
        dao.insert(entity("7"))

        assertEquals(1, dao.getAll().size)
    }

    @Test
    fun getAll_returnsAllInsertedEntities() = runTest {
        dao.insert(entity("a"))
        dao.insert(entity("b"))
        dao.insert(entity("c"))

        val ids = dao.getAll().map { it.id }.toSet()
        assertEquals(setOf("a", "b", "c"), ids)
    }
}

