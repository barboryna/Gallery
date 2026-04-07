package com.barbora.gallery.home.favorites

import com.barbora.gallery.core.photo.Photo
import com.barbora.gallery.core.photo.PhotoRepository
import javax.inject.Inject

class GetFavoritesUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(): List<Photo> = repository.getFavorites()
}

