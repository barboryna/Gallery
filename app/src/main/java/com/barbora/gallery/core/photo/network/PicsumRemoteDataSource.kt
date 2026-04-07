package com.barbora.gallery.core.photo.network

import javax.inject.Inject

class PicsumRemoteDataSource @Inject constructor(
    private val api: PicsumApiService
) : RemotePhotoDataSource {
    override suspend fun getPhotos(page: Int, limit: Int): List<PhotoResponse> =
        api.getPhotos(page, limit)

    override suspend fun getPhoto(id: String): PhotoResponse =
        api.getPhoto(id)
}
