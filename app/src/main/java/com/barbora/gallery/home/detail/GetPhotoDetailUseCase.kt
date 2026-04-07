package com.barbora.gallery.home.detail

import com.barbora.gallery.core.photo.Photo
import com.barbora.gallery.core.photo.PhotoRepository
import javax.inject.Inject

class GetPhotoDetailUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(id: String): Photo = repository.getPhoto(id)
}

