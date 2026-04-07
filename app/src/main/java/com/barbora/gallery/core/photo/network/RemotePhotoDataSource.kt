package com.barbora.gallery.core.photo.network

interface RemotePhotoDataSource {
    suspend fun getPhotos(page: Int, limit: Int): List<PhotoResponse>

    suspend fun getPhoto(id: String): PhotoResponse
}

