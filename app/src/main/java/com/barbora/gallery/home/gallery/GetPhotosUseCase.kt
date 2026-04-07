package com.barbora.gallery.home.gallery

import com.barbora.gallery.core.photo.Photo
import com.barbora.gallery.core.photo.PhotoRepository
import javax.inject.Inject

private const val DEFAULT_PHOTO_LIMIT = 30

class GetPhotosUseCase @Inject constructor(
    private val repository: PhotoRepository
) {
    suspend operator fun invoke(page: Int, limit: Int = DEFAULT_PHOTO_LIMIT): List<Photo> =
        repository.getPhotos(page, limit)
}

