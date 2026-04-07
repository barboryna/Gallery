package com.barbora.gallery.home.favorites

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.barbora.gallery.home.HomeViewModel
import com.barbora.gallery.util.compose.navigation.ScreenConfig
import kotlinx.serialization.Serializable

object FavoritesScreen {
    @Serializable
    object Config : ScreenConfig

    fun build(builder: NavGraphBuilder, navigationViewModel: HomeViewModel) {
        builder.composable<Config> { Screen(navigationViewModel) }
    }
}

@Composable
private fun Screen(
    navigationViewModel: HomeViewModel,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    // TODO: collect uiState and render list of favorited photos
}
