package com.curato.wallpapers.ui.screens.explore

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
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExploreUiData(
    val wallpapers: List<Wallpaper> = emptyList(),
    val searchQuery: String = "",
    val selectedCategory: WallpaperCategory? = null,
    val isSearchActive: Boolean = false,
    val isLoadingMore: Boolean = false,
    val hasNextPage: Boolean = false,
    val currentPage: Int = 1,
)

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val wallpaperRepository: WallpaperRepository,
    private val favoriteRepository: FavoriteRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<ExploreUiData>>(UiState.Loading)
    val uiState: StateFlow<UiState<ExploreUiData>> = _uiState.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    private var searchJob: Job? = null
    private var loadCuratedJob: Job? = null

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
        dispatch(WallpaperAction.LoadCurated)
    }

    private fun applyFavorites(wallpapers: List<Wallpaper>) =
        wallpapers.map { it.copy(isFavorite = it.id in _favoriteIds.value) }

    fun dispatch(action: WallpaperAction) {
        when (action) {
            is WallpaperAction.LoadCurated -> loadCurated()
            is WallpaperAction.Search -> onSearch(action.query)
            is WallpaperAction.ClearSearch -> clearSearch()
            is WallpaperAction.FilterByCategory -> filterByCategory(action.category)
            is WallpaperAction.ClearFilter -> clearFilter()
            is WallpaperAction.LoadNextPage -> loadNextPage()
            is WallpaperAction.ToggleFavorite -> toggleFavorite(action.wallpaper)
            is WallpaperAction.Retry -> dispatch(action.previousAction)
            else -> Unit
        }
    }

    private fun loadCurated(page: Int = 1, append: Boolean = false) {
        loadCuratedJob?.cancel()
        loadCuratedJob = viewModelScope.launch {
            if (append) {
                _uiState.update { state ->
                    val data = (state as? UiState.Success)?.data ?: return@update state
                    UiState.Success(data.copy(isLoadingMore = true))
                }
            } else {
                // Only show full loading spinner if there's nothing to show yet
                val hasContent = (_uiState.value as? UiState.Success)?.data?.wallpapers?.isNotEmpty() == true
                if (!hasContent) _uiState.value = UiState.Loading
            }
            wallpaperRepository.getCurated(page).onSuccess { result ->
                _uiState.update { state ->
                    val current = (state as? UiState.Success)?.data ?: ExploreUiData()
                    UiState.Success(
                        current.copy(
                            wallpapers = if (append) current.wallpapers + applyFavorites(result.items) else applyFavorites(result.items),
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

    private fun onSearch(query: String) {
        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: ExploreUiData()
            UiState.Success(data.copy(searchQuery = query, isSearchActive = query.isNotBlank()))
        }
        if (query.isBlank()) {
            searchJob?.cancel()
            loadCurated()
            return
        }
        searchJob?.cancel()
        loadCuratedJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(400) // debounce
            wallpaperRepository.search(query).onSuccess { result ->
                _uiState.update { state ->
                    val data = (state as? UiState.Success)?.data ?: ExploreUiData()
                    UiState.Success(
                        data.copy(
                            wallpapers = applyFavorites(result.items),
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

    private fun clearSearch() {
        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: ExploreUiData()
            UiState.Success(data.copy(searchQuery = "", isSearchActive = false))
        }
        loadCurated()
    }

    private fun filterByCategory(category: WallpaperCategory) {
        viewModelScope.launch {
            _uiState.update { state ->
                val data = (state as? UiState.Success)?.data ?: ExploreUiData()
                UiState.Success(data.copy(selectedCategory = category, isLoadingMore = true))
            }
            wallpaperRepository.getByCategory(category, page = 1).onSuccess { result ->
                _uiState.update { state ->
                    val data = (state as? UiState.Success)?.data ?: ExploreUiData()
                    UiState.Success(
                        data.copy(
                            wallpapers = applyFavorites(result.items),
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

    private fun clearFilter() {
        _uiState.update { state ->
            val data = (state as? UiState.Success)?.data ?: ExploreUiData()
            UiState.Success(data.copy(selectedCategory = null))
        }
        loadCurated()
    }

    private fun loadNextPage() {
        val current = (_uiState.value as? UiState.Success)?.data ?: return
        if (!current.hasNextPage || current.isLoadingMore) return
        val nextPage = current.currentPage + 1
        viewModelScope.launch {
            _uiState.update { state ->
                val data = (state as? UiState.Success)?.data ?: return@update state
                UiState.Success(data.copy(isLoadingMore = true))
            }
            val result = when {
                current.searchQuery.isNotBlank() -> wallpaperRepository.search(current.searchQuery, nextPage)
                current.selectedCategory != null -> wallpaperRepository.getByCategory(current.selectedCategory, nextPage)
                else -> wallpaperRepository.getCurated(nextPage)
            }
            result.onSuccess { paginated ->
                _uiState.update { state ->
                    val data = (state as? UiState.Success)?.data ?: ExploreUiData()
                    UiState.Success(
                        data.copy(
                            wallpapers = data.wallpapers + applyFavorites(paginated.items),
                            isLoadingMore = false,
                            hasNextPage = paginated.hasNextPage,
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
        viewModelScope.launch { favoriteRepository.toggleFavorite(wallpaper) }
    }
}
