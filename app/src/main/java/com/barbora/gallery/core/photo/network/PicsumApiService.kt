package com.barbora.gallery.core.photo.network

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PicsumApiService {
    @GET("v2/list")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<PhotoResponse>

    @GET("id/{id}/info")
    suspend fun getPhoto(@Path("id") id: String): PhotoResponse
}
