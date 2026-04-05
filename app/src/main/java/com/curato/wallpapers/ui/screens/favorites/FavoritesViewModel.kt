package com.curato.wallpapers.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curato.wallpapers.data.repository.FavoriteRepository
import com.curato.wallpapers.domain.common.WallpaperAction
import com.curato.wallpapers.domain.model.Wallpaper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FavoritesUiData(
    val favorites: List<Wallpaper> = emptyList(),
)

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    // Favorites are a live Room Flow — no manual loading needed
    val uiState: StateFlow<FavoritesUiData> = favoriteRepository
        .observeFavorites()
        .map { FavoritesUiData(favorites = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FavoritesUiData(),
        )

    fun dispatch(action: WallpaperAction) {
        when (action) {
            is WallpaperAction.ToggleFavorite -> toggleFavorite(action.wallpaper)
            else -> Unit
        }
    }

    private fun toggleFavorite(wallpaper: Wallpaper) {
        viewModelScope.launch { favoriteRepository.toggleFavorite(wallpaper) }
    }
}
