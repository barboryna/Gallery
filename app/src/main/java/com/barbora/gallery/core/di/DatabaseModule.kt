package com.barbora.gallery.core.di

import android.content.Context
import androidx.room.Room
import com.barbora.gallery.core.database.FavoriteDao
import com.barbora.gallery.core.database.GalleryDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): GalleryDatabase =
        Room.databaseBuilder(context, GalleryDatabase::class.java, "gallery.db").build()

    @Provides
    @Singleton
    fun provideFavoriteDao(database: GalleryDatabase): FavoriteDao = database.favoriteDao()
}

