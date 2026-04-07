package com.barbora.gallery.home.gallery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barbora.gallery.core.photo.Photo
import com.barbora.gallery.home.HomeViewModel
import com.barbora.gallery.ui.theme.GalleryTheme
import com.barbora.gallery.util.compose.GalleryImage
import com.barbora.gallery.util.compose.navigation.ScreenConfig
import kotlinx.serialization.Serializable

object GalleryScreen {
    @Serializable
    object Config : ScreenConfig

    fun build(builder: NavGraphBuilder, navigationViewModel: HomeViewModel) {
        builder.composable<Config> { Screen(navigationViewModel) }
    }
}

@Composable
private fun Screen(
    navigationViewModel: HomeViewModel,
    viewModel: GalleryViewModel = hiltViewModel()
) {
    Content(
        photoList = viewModel.photoList.value,
        loading = viewModel.loading.value,
        error = viewModel.error.value,
        onPhotoClick = navigationViewModel::onPhotoClick,
        onRetryClick = viewModel::onRetryClick
    )
}

@Composable
private fun Content(
    photoList: List<Photo>,
    loading: Boolean,
    error: String?,
    onPhotoClick: (String) -> Unit,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars)
    ) {
        when {
            error != null -> ErrorContent(message = error, onRetryClick = onRetryClick)
            loading -> LoadingContent()
            photoList.isEmpty() -> EmptyContent()
            else -> GalleryContent(photoList, onPhotoClick)
        }
    }
}

@Composable
private fun GalleryContent(
    photoList: List<Photo>,
    onPhotoClick: (String) -> Unit
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(photoList, key = { it.id }) { photo ->
            GalleryImage(
                photo = photo,
                onClick = { onPhotoClick(photo.id) }
            )
        }
    }
}

@Composable
private fun EmptyContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "No images available",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    message: String,
    onRetryClick: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = message
            )
            Button(onClick = onRetryClick) {
                Text(text = "Retry")
            }
        }
    }
}

private val previewPhotos = List(30) { index ->
    Photo(
        id = "$index",
        author = "Author $index",
        width = 800,
        height = 600,
        url = "https://picsum.photos/id/$index/800/600",
        downloadUrl = "https://picsum.photos/id/$index/800/600",
        isFavorite = index % 2 == 0
    )
}

@Preview(showBackground = true, name = "Gallery — Success")
@Composable
private fun GallerySuccessPreview() {
    GalleryTheme {
        Content(
            photoList = previewPhotos,
            loading = false,
            error = null,
            onPhotoClick = {},
            onRetryClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Gallery — Loading")
@Composable
private fun GalleryLoadingPreview() {
    GalleryTheme {
        Content(
            photoList = emptyList(),
            loading = true,
            error = null,
            onPhotoClick = {},
            onRetryClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Gallery — Error")
@Composable
private fun GalleryErrorPreview() {
    GalleryTheme {
        Content(
            photoList = emptyList(),
            loading = false,
            error = "Failed to load images. Please check your connection.",
            onPhotoClick = {},
            onRetryClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Gallery — Empty")
@Composable
private fun GalleryEmptyPreview() {
    GalleryTheme {
        Content(
            photoList = emptyList(),
            loading = false,
            error = null,
            onPhotoClick = {},
            onRetryClick = {}
        )
    }
}
