package com.barbora.gallery.home

import androidx.lifecycle.ViewModel
import com.barbora.gallery.home.detail.PhotoDetailScreen
import com.barbora.gallery.util.compose.navigation.ScreenConfig
import com.barbora.gallery.util.state.mutableEffectOf
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    val navigate = mutableEffectOf<ScreenConfig>()
    val navigateBack = mutableEffectOf<Unit>()

    fun onPhotoClick(photoId: String) {
        navigate.value = PhotoDetailScreen.Config(photoId = photoId)
    }

    fun onNavigateBack() {
        navigateBack.value = Unit
    }
}

