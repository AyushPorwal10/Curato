package com.curato.wallpapers.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.curato.wallpapers.ui.components.CuratoTopBar
import com.curato.wallpapers.ui.components.SectionHeader
import com.curato.wallpapers.ui.components.TrendingCard
import com.curato.wallpapers.ui.components.WallpaperCard
import com.curato.wallpapers.ui.theme.curatoColors

@Composable
fun HomeScreen(
    onWallpaperClick: (String) -> Unit,
    onSeeAllTrending: () -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

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
                    contentPadding = PaddingValues(bottom = 100.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    // Top bar
                    item {
                        CuratoTopBar(title = "Curato")
                    }

                    // Trending section
                    item {
                        Spacer(Modifier.height(8.dp))
                        SectionHeader(
                            title = "Trending",
                            eyebrow = "Curated Selection",
                            actionLabel = "See all",
                            onAction = onSeeAllTrending,
                            modifier = Modifier.padding(horizontal = 24.dp),
                        )
                        Spacer(Modifier.height(16.dp))
                    }

                    // Trending horizontal scroll
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 24.dp),
                        ) {
                            items(data.trendingWallpapers) { wallpaper ->
                                TrendingCard(
                                    wallpaper = wallpaper,
                                    onClick = { onWallpaperClick(wallpaper.id) },
                                )
                            }
                        }
                    }

                    // Category chips
                    item {
                        Spacer(Modifier.height(28.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            contentPadding = PaddingValues(horizontal = 24.dp),
                        ) {
                            items(WallpaperCategory.entries) { category ->
                                CategoryChip(
                                    category = category,
                                    selected = data.selectedCategory == category,
                                    onSelect = {
                                        viewModel.dispatch(WallpaperAction.FilterByCategory(it))
                                    },
                                )
                            }
                        }
                    }

                    // For You section header
                    item {
                        Spacer(Modifier.height(28.dp))
                        SectionHeader(
                            title = "For You",
                            subtitle = "Based on your recent likes",
                            modifier = Modifier.padding(horizontal = 24.dp),
                        )
                        Spacer(Modifier.height(16.dp))
                    }

                    // For You 2-column grid (non-lazy inside lazy — use chunked)
                    items(data.forYouWallpapers.chunked(2)) { row ->
                        androidx.compose.foundation.layout.Row(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
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
                            // Fill empty slot in last row if odd count
                            if (row.size == 1) Spacer(Modifier.weight(1f))
                        }
                        Spacer(Modifier.height(16.dp))
                    }

                    // Load more trigger
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
