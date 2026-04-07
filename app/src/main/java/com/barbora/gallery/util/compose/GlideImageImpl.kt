package com.barbora.gallery.util.compose

import android.graphics.Color
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import androidx.core.graphics.drawable.toDrawable

private val loadingPlaceholder = Color.DKGRAY.toDrawable()
private val errorPlaceholder = Color.DKGRAY.toDrawable()

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun GlideImageImpl(
    url: String,
    contentDescription: String,
    contentScale: ContentScale,
    modifier: Modifier
) {
    GlideImage(
        model = url,
        contentDescription = contentDescription,
        contentScale = contentScale,
        modifier = modifier,
        loading = placeholder(loadingPlaceholder),
        failure = placeholder(errorPlaceholder)
    )
}
