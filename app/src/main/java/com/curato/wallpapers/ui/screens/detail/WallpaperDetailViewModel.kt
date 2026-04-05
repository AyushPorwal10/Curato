package com.curato.wallpapers.ui.screens.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curato.wallpapers.data.repository.FavoriteRepository
import com.curato.wallpapers.data.repository.WallpaperRepository
import com.curato.wallpapers.domain.common.UiState
import com.curato.wallpapers.domain.common.WallpaperAction
import com.curato.wallpapers.domain.common.onError
import com.curato.wallpapers.domain.common.onSuccess
import com.curato.wallpapers.domain.common.toUiState
import com.curato.wallpapers.domain.model.Wallpaper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailUiData(
    val wallpaper: Wallpaper,
    val isFavorite: Boolean = false,
    val isApplying: Boolean = false,
    val isDownloading: Boolean = false,
)

@HiltViewModel
class WallpaperDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val wallpaperRepository: WallpaperRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    private val wallpaperId: String = checkNotNull(savedStateHandle["wallpaperId"])

    private val _uiState = MutableStateFlow<UiState<DetailUiData>>(UiState.Loading)
    val uiState: StateFlow<UiState<DetailUiData>> = _uiState.asStateFlow()

    init {
        loadDetail()
        observeFavoriteStatus()
    }

    fun dispatch(action: WallpaperAction) {
        when (action) {
            is WallpaperAction.ToggleFavorite -> toggleFavorite(action.wallpaper)
            is WallpaperAction.Retry -> loadDetail()
            else -> Unit
        }
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            wallpaperRepository.getById(wallpaperId).onSuccess { wallpaper ->
                val isFav = favoriteRepository.observeIsFavorite(wallpaperId).first()
                _uiState.value = UiState.Success(DetailUiData(wallpaper = wallpaper, isFavorite = isFav))
            }.onError { _, message ->
                _uiState.value = UiState.Error(message)
            }
        }
    }

    private fun observeFavoriteStatus() {
        viewModelScope.launch {
            favoriteRepository.observeIsFavorite(wallpaperId).collect { isFav ->
                _uiState.update { state ->
                    val data = (state as? UiState.Success)?.data ?: return@update state
                    UiState.Success(data.copy(isFavorite = isFav))
                }
            }
        }
    }

    private fun toggleFavorite(wallpaper: Wallpaper) {
        viewModelScope.launch { favoriteRepository.toggleFavorite(wallpaper) }
    }
}
