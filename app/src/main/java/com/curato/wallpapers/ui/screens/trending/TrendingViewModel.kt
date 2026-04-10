package com.curato.wallpapers.ui.screens.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curato.wallpapers.data.repository.FavoriteRepository
import com.curato.wallpapers.data.repository.WallpaperRepository
import com.curato.wallpapers.domain.common.UiState
import com.curato.wallpapers.domain.common.WallpaperAction
import com.curato.wallpapers.domain.common.onError
import com.curato.wallpapers.domain.common.onSuccess
import com.curato.wallpapers.domain.model.Wallpaper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class TrendingUiData(
    val wallpapers: List<Wallpaper> = emptyList(),
    val isLoadingMore: Boolean = false,
    val hasNextPage: Boolean = false,
    val currentPage: Int = 1,
)

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val wallpaperRepository: WallpaperRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<TrendingUiData>>(UiState.Loading)
    val uiState: StateFlow<UiState<TrendingUiData>> = _uiState.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())

    init {
        viewModelScope.launch {
            favoriteRepository.observeFavorites().collect { favorites ->
                _favoriteIds.value = favorites.map { it.id }.toSet()
                _uiState.update { state ->
                    if (state is UiState.Success) {
                        val ids = _favoriteIds.value
                        UiState.Success(
                            state.data.copy(
                                wallpapers = state.data.wallpapers.map { it.copy(isFavorite = it.id in ids) }
                            )
                        )
                    } else state
                }
            }
        }
        loadPage(page = 1, append = false)
    }

    fun dispatch(action: WallpaperAction) {
        when (action) {
            is WallpaperAction.LoadNextPage -> loadNextPage()
            is WallpaperAction.ToggleFavorite -> toggleFavorite(action.wallpaper)
            is WallpaperAction.Retry -> loadPage(page = 1, append = false)
            else -> Unit
        }
    }

    private fun loadNextPage() {
        val current = (_uiState.value as? UiState.Success)?.data ?: return
        if (!current.hasNextPage || current.isLoadingMore) return
        loadPage(page = current.currentPage + 1, append = true)
    }

    private fun loadPage(page: Int, append: Boolean) {
        viewModelScope.launch {
            if (!append) {
                _uiState.value = UiState.Loading
            } else {
                _uiState.update { state ->
                    val data = (state as? UiState.Success)?.data ?: return@update state
                    UiState.Success(data.copy(isLoadingMore = true))
                }
            }
            wallpaperRepository.getCurated(page = page).onSuccess { result ->
                _uiState.update { state ->
                    val current = (state as? UiState.Success)?.data ?: TrendingUiData()
                    UiState.Success(
                        current.copy(
                            wallpapers = if (append) current.wallpapers + applyFavorites(result.items)
                                         else applyFavorites(result.items),
                            isLoadingMore = false,
                            hasNextPage = result.hasNextPage,
                            currentPage = result.currentPage,
                        )
                    )
                }
            }.onError { _, message ->
                _uiState.value = UiState.Error(message)
            }
        }
    }

    private fun toggleFavorite(wallpaper: Wallpaper) {
        viewModelScope.launch { favoriteRepository.toggleFavorite(wallpaper) }
    }

    private fun applyFavorites(wallpapers: List<Wallpaper>) =
        wallpapers.map { it.copy(isFavorite = it.id in _favoriteIds.value) }
}
