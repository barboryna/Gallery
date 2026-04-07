package com.barbora.gallery.home.detail

import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barbora.gallery.core.photo.Photo
import com.barbora.gallery.home.HomeViewModel
import com.barbora.gallery.ui.theme.GalleryTheme
import com.barbora.gallery.util.compose.AppImage
import com.barbora.gallery.util.compose.navigation.ScreenConfig
import kotlinx.serialization.Serializable

object PhotoDetailScreen {
    @Serializable
    data class Config(val photoId: String) : ScreenConfig

    fun build(builder: NavGraphBuilder, navigationViewModel: HomeViewModel) {
        builder.composable<Config>(
            enterTransition = { slideInVertically(initialOffsetY = { it }) },
            exitTransition = { slideOutVertically(targetOffsetY = { it }) },
            popEnterTransition = { slideInVertically(initialOffsetY = { it }) },
            popExitTransition = { slideOutVertically(targetOffsetY = { it }) }
        ) {
            Screen(navigationViewModel = navigationViewModel)
        }
    }
}

@Composable
private fun Screen(
    navigationViewModel: HomeViewModel,
    viewModel: PhotoDetailViewModel = hiltViewModel()
) {
    Content(
        photo = viewModel.photo.value,
        loading = viewModel.loading.value,
        error = viewModel.error.value,
        onClose = navigationViewModel::onNavigateBack
    )
}

@Composable
private fun Content(
    photo: Photo?,
    loading: Boolean,
    error: String?,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .windowInsetsPadding(WindowInsets.statusBars)
    ) {
        when {
            loading -> LoadingContent()
            error != null -> ErrorContent(error)
            photo != null -> PhotoContent(photo = photo, onClose = onClose)
        }
    }
}

@Composable
private fun PhotoContent(
    photo: Photo,
    onClose: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black),
            contentAlignment = Alignment.Center
        ) {
            AppImage(
                modifier = Modifier.fillMaxWidth(),
                url = photo.downloadUrl,
                contentDescription = photo.author,
                contentScale = ContentScale.Inside
            )
            CloseButton(
                modifier = Modifier.align(Alignment.TopEnd),
                onClose = onClose
            )
        }
        DetailsContent(photo = photo)
    }
}

@Composable
private fun CloseButton(
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    IconButton(
        modifier = modifier.padding(4.dp),
        onClick = onClose
    ) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Close",
            tint = Color.White
        )
    }
}

@Composable
private fun DetailsContent(photo: Photo) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        HorizontalDivider(color = Color.DarkGray)
        Spacer(modifier = Modifier.height(4.dp))
        DetailRow(label = "Author", value = photo.author)
        DetailRow(label = "ID", value = photo.id)
        DetailRow(label = "Dimensions", value = "${photo.width} × ${photo.height} px")
        DetailRow(label = "Source", value = photo.url)
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = Color.Gray
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White
        )
    }
}

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(color = Color.White)
    }
}

@Composable
private fun ErrorContent(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.padding(16.dp),
            text = message,
            color = MaterialTheme.colorScheme.error
        )
    }
}

private val previewPhoto = Photo(
    id = "237",
    author = "Alejandro Escamilla",
    width = 3888,
    height = 2592,
    url = "https://unsplash.com/photos/yC-Yzbqy7PY",
    downloadUrl = "https://picsum.photos/id/237/3888/2592",
    isFavorite = false
)

@Preview(name = "Detail — Success", showBackground = true)
@Composable
private fun PhotoDetailSuccessPreview() {
    GalleryTheme {
        Content(
            photo = previewPhoto,
            loading = false,
            error = null,
            onClose = {}
        )
    }
}

@Preview(name = "Detail — Loading", showBackground = true)
@Composable
private fun PhotoDetailLoadingPreview() {
    GalleryTheme {
        Content(
            photo = null,
            loading = true,
            error = null,
            onClose = {}
        )
    }
}

@Preview(name = "Detail — Error", showBackground = true)
@Composable
private fun PhotoDetailErrorPreview() {
    GalleryTheme {
        Content(
            photo = null,
            loading = false,
            error = "Failed to load photo.",
            onClose = {}
        )
    }
}
