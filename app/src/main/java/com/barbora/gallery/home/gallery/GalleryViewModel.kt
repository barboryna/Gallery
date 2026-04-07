package com.barbora.gallery.home.gallery

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.barbora.gallery.core.photo.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GalleryViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : ViewModel() {
    val loading = mutableStateOf(false)
    val photoList = mutableStateOf<List<Photo>>(emptyList())
    val error = mutableStateOf<String?>(null)

    init {
        loadPhotos()
    }

    fun loadPhotos() {
        viewModelScope.launch {
            try {
                loading.value = true
                error.value = null
                photoList.value = getPhotosUseCase(page = 1)
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                loading.value = false
            }
        }
    }

    fun onRetryClick() {
        loadPhotos()
    }
}

