package com.barbora.gallery.util.compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

@Composable
fun AppImage(
    url: String,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    GlideImageImpl(
        url = url,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier
    )
}
