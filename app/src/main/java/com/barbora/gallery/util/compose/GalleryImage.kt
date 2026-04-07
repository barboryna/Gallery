package com.barbora.gallery.util.compose

import android.graphics.drawable.ColorDrawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.barbora.gallery.core.photo.Photo
import com.barbora.gallery.core.photo.thumbnailUrl

@Composable
fun GalleryImage(
    photo: Photo,
    onClick: () -> Unit
) {
    AppImage(
        url = photo.thumbnailUrl(),
        contentDescription = photo.author,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        contentScale = ContentScale.Crop
    )
}
