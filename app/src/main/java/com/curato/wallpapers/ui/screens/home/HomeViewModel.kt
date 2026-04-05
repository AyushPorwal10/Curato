package com.curato.wallpapers.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.curato.wallpapers.data.repository.FavoriteRepository
import com.curato.wallpapers.data.repository.WallpaperRepository
import com.curato.wallpapers.domain.common.UiState
import com.curato.wallpapers.domain.common.WallpaperAction
import com.curato.wallpapers.domain.common.onError
import com.curato.wallpapers.domain.common.onSuccess
import com.curato.wallpapers.domain.model.Wallpaper
import com.curato.wallpapers.domain.model.WallpaperCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiData(
    val trendingWallpapers: List<Wallpaper> = emptyList(),
    val forYouWallpapers: List<Wallpaper> = emptyList(),
    val selectedCategory: WallpaperCategory = WallpaperCategory.AMOLED,
    val isLoadingMore: Boolean = false,
    val hasNextPage: Boolean = false,
    val currentPage: Int = 1,
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val wallpaperRepository: WallpaperRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<HomeUiData>>(UiState.Loading)
    val uiState: StateFlow<UiState<HomeUiData>> = _uiState.asStateFlow()

    init {
        dispatch(WallpaperAction.LoadCurated)
    }

    fun dispatch(action: WallpaperAction) {
        when (action) {
            is WallpaperAction.LoadCurated -> loadInitial()
            is WallpaperAction.FilterByCategory -> loadByCategory(action.category)
            is WallpaperAction.LoadNextPage -> loadNextPage()
            is WallpaperAction.ToggleFavorite -> toggleFavorite(action.wallpaper)
            is WallpaperAction.Retry -> dispatch(action.previousAction)
            else -> Unit
        }
    }

    private fun loadInitial() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            wallpaperRepository.getCurated(page = 1).onSuccess { result ->
                val trending = result.items.take(6)
                val forYou = result.items.drop(6)
                _uiState.value = UiState.Success(
                    HomeUiData(
                        trendingWallpapers = trending,
                        forYouWallpapers = forYou,
                        hasNextPage = result.hasNextPage,
                        currentPage = result.currentPage,
                    )
                )
            }.onError { _, message ->
                _uiState.value = UiState.Error(message)
            }
        }
    }

    private fun loadByCategory(category: WallpaperCategory) {
        viewModelScope.launch {
            val current = (_uiState.value as? UiState.Success)?.data ?: HomeUiData()
            _uiState.value = UiState.Success(current.copy(selectedCategory = category, isLoadingMore = true))
            wallpaperRepository.getByCategory(category, page = 1).onSuccess { result ->
                _uiState.update { state ->
                    val data = (state as? UiState.Success)?.data ?: HomeUiData()
                    UiState.Success(
                        data.copy(
                            forYouWallpapers = result.items,
                            isLoadingMore = false,
                            hasNextPage = result.hasNextPage,
                            currentPage = 1,
                        )
                    )
                }
            }.onError { _, message ->
                _uiState.value = UiState.Error(message)
            }
        }
    }

    private fun loadNextPage() {
        val current = (_uiState.value as? UiState.Success)?.data ?: return
        if (!current.hasNextPage || current.isLoadingMore) return
        viewModelScope.launch {
            _uiState.value = UiState.Success(current.copy(isLoadingMore = true))
            val nextPage = current.currentPage + 1
            wallpaperRepository.getByCategory(current.selectedCategory, page = nextPage)
                .onSuccess { result ->
                    _uiState.update { state ->
                        val data = (state as? UiState.Success)?.data ?: HomeUiData()
                        UiState.Success(
                            data.copy(
                                forYouWallpapers = data.forYouWallpapers + result.items,
                                isLoadingMore = false,
                                hasNextPage = result.hasNextPage,
                                currentPage = nextPage,
                            )
                        )
                    }
                }.onError { _, _ ->
                    _uiState.update { state ->
                        val data = (state as? UiState.Success)?.data ?: return@update state
                        UiState.Success(data.copy(isLoadingMore = false))
                    }
                }
        }
    }

    private fun toggleFavorite(wallpaper: Wallpaper) {
        viewModelScope.launch {
            favoriteRepository.toggleFavorite(wallpaper)
        }
    }
}
