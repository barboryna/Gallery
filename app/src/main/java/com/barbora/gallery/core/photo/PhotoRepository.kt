package com.barbora.gallery.core.photo

import com.barbora.gallery.core.database.FavoriteDao
import com.barbora.gallery.core.photo.network.RemotePhotoDataSource
import javax.inject.Inject

interface PhotoRepository {
    suspend fun getPhotos(page: Int, limit: Int): List<Photo>

    suspend fun getPhoto(id: String): Photo

    suspend fun getFavorites(): List<Photo>

    suspend fun toggleFavorite(photo: Photo)
}

class PhotoRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemotePhotoDataSource,
    private val favoriteDao: FavoriteDao
) : PhotoRepository {
    override suspend fun getPhotos(page: Int, limit: Int): List<Photo> {
        val favoriteIds = favoriteDao.getAll().map { it.id }.toSet()
        return remoteDataSource.getPhotos(page, limit).map { response ->
            Photo(
                response = response,
                isFavorite = response.id in favoriteIds
            )
        }
    }

    override suspend fun getPhoto(id: String): Photo {
        val isFavorite = favoriteDao.isFavorite(id)
        return Photo(
            response = remoteDataSource.getPhoto(id),
            isFavorite = isFavorite
        )
    }

    override suspend fun getFavorites(): List<Photo> {
        // TODO: implement
        return emptyList()
    }

    override suspend fun toggleFavorite(photo: Photo) {
        // TODO: implement
    }
}
