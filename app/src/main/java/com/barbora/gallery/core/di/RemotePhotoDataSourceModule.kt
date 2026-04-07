package com.barbora.gallery.core.di

import com.barbora.gallery.core.photo.network.PicsumApiService
import com.barbora.gallery.core.photo.network.PicsumRemoteDataSource
import com.barbora.gallery.core.photo.network.RemotePhotoDataSource
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemotePhotoDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindRemotePhotoDataSource(impl: PicsumRemoteDataSource): RemotePhotoDataSource

    companion object {
        const val BASE_URL = "https://picsum.photos/"

        @Provides
        @Singleton
        fun providePicsumApiService(retrofit: Retrofit): PicsumApiService =
            retrofit.create(PicsumApiService::class.java)
    }
}
