package com.curato.wallpapers.ui.screens.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.curato.wallpapers.domain.common.UiState
import com.curato.wallpapers.domain.common.WallpaperAction
import com.curato.wallpapers.domain.model.WallpaperCategory
import com.curato.wallpapers.ui.components.CategoryChip
import com.curato.wallpapers.ui.components.CuratoLoadingIndicator
import com.curato.wallpapers.ui.components.CuratoSearchBar
import com.curato.wallpapers.ui.components.CuratoTopBar
import com.curato.wallpapers.ui.components.SectionHeader
import com.curato.wallpapers.ui.components.WallpaperCard
import com.curato.wallpapers.ui.theme.curatoColors

@Composable
fun ExploreScreen(
    onWallpaperClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExploreViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

    // Infinite scroll trigger — load next page when near bottom
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisible = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: 0
            val total = listState.layoutInfo.totalItemsCount
            lastVisible >= total - 4
        }
    }
    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) viewModel.dispatch(WallpaperAction.LoadNextPage)
    }

    val colors = curatoColors
    Box(modifier = modifier.fillMaxSize()) {
        when (val state = uiState) {
            is UiState.Loading -> CuratoLoadingIndicator(
                modifier = Modifier.align(Alignment.Center),
            )

            is UiState.Error -> Text(
                text = state.message,
                color = colors.onSurface,
                modifier = Modifier.align(Alignment.Center),
            )

            is UiState.Success -> {
                val data = state.data
                LazyColumn(
                    state = listState,
                    contentPadding = PaddingValues(bottom = 100.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item { CuratoTopBar(title = "Explore") }

                    // Search bar
                    item {
                        Spacer(Modifier.height(8.dp))
                        CuratoSearchBar(
                            query = data.searchQuery,
                            onQueryChange = { viewModel.dispatch(WallpaperAction.Search(it)) },
                            onSearch = { viewModel.dispatch(WallpaperAction.Search(it)) },
                            modifier = Modifier.padding(horizontal = 24.dp),
                        )
                    }

                    // Category chips
                    item {
                        Spacer(Modifier.height(16.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(horizontal = 24.dp),
                        ) {
                            items(WallpaperCategory.entries) { category ->
                                CategoryChip(
                                    category = category,
                                    selected = data.selectedCategory == category,
                                    onSelect = {
                                        if (data.selectedCategory == it) {
                                            viewModel.dispatch(WallpaperAction.ClearFilter)
                                        } else {
                                            viewModel.dispatch(WallpaperAction.FilterByCategory(it))
                                        }
                                    },
                                )
                            }
                        }
                    }

                    // Section header
                    item {
                        Spacer(Modifier.height(24.dp))
                        SectionHeader(
                            title = if (data.isSearchActive) "Results" else "Explore All",
                            eyebrow = "Curated",
                            modifier = Modifier.padding(horizontal = 24.dp),
                        )
                        Spacer(Modifier.height(16.dp))
                    }

                    // Asymmetric 2-column grid (chunked rows)
                    items(data.wallpapers.chunked(2)) { row ->
                        androidx.compose.foundation.layout.Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(horizontal = 24.dp),
                        ) {
                            row.forEach { wallpaper ->
                                WallpaperCard(
                                    wallpaper = wallpaper,
                                    onClick = { onWallpaperClick(wallpaper.id) },
                                    onFavoriteClick = {
                                        viewModel.dispatch(WallpaperAction.ToggleFavorite(wallpaper))
                                    },
                                    modifier = Modifier.weight(1f),
                                )
                            }
                            if (row.size == 1) Spacer(Modifier.weight(1f))
                        }
                        Spacer(Modifier.height(12.dp))
                    }

                    if (data.isLoadingMore) {
                        item {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier.padding(16.dp),
                            ) {
                                CuratoLoadingIndicator()
                            }
                        }
                    }
                }
            }

            else -> Unit
        }
    }
}
