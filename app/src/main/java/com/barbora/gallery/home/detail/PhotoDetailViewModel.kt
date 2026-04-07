package com.barbora.gallery.home.detail

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.barbora.gallery.core.photo.Photo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PhotoDetailViewModel @Inject constructor(
    private val getPhotoDetailUseCase: GetPhotoDetailUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val photoId: String = savedStateHandle.toRoute<PhotoDetailScreen.Config>().photoId
    val loading = mutableStateOf(false)
    val photo = mutableStateOf<Photo?>(null)
    val error = mutableStateOf<String?>(null)

    init {
        loadPhoto()
    }

    fun loadPhoto() {
        viewModelScope.launch {
            try {
                loading.value = true
                error.value = null
                photo.value = getPhotoDetailUseCase(photoId)
            } catch (e: Exception) {
                error.value = e.message
            } finally {
                loading.value = false
            }
        }
    }
}
