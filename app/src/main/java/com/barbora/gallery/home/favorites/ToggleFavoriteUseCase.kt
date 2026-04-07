package com.barbora.gallery.home.favorites

import com.barbora.gallery.core.photo.Photo
import com.barbora.gallery.core.photo.PhotoRepository
import javax.inject.Inject

class ToggleFavoriteUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(photo: Photo) {
        repository.toggleFavorite(photo)
    }
}

