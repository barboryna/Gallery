package com.barbora.gallery.core.photo

import com.barbora.gallery.core.photo.network.PhotoResponse

private const val THUMBNAIL_SIZE = 600

data class Photo(
    val id: String,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val downloadUrl: String,
    val isFavorite: Boolean = false
)

fun Photo(response: PhotoResponse, isFavorite: Boolean = false): Photo = Photo(
    id = response.id,
    author = response.author,
    width = response.width,
    height = response.height,
    url = response.url,
    downloadUrl = response.downloadUrl,
    isFavorite = isFavorite
)

fun Photo.thumbnailUrl(
    widthPx: Int = THUMBNAIL_SIZE,
    heightPx: Int = THUMBNAIL_SIZE
): String =
    "https://picsum.photos/id/$id/$widthPx/$heightPx"
