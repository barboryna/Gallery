package com.barbora.gallery.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.barbora.gallery.home.detail.PhotoDetailScreen
import com.barbora.gallery.home.favorites.FavoritesScreen
import com.barbora.gallery.home.gallery.GalleryScreen
import com.barbora.gallery.util.state.StateTriggeredEffect

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    StateTriggeredEffect(viewModel.navigate) { destination ->
        navController.navigate(destination)
    }
    StateTriggeredEffect(viewModel.navigateBack) {
        navController.popBackStack()
    }

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = GalleryScreen.Config
    ) {
        GalleryScreen.build(this, viewModel)
        FavoritesScreen.build(this, viewModel)
        PhotoDetailScreen.build(this, viewModel)
    }
}
