package com.curato.wallpapers.ui.screens.trending

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.curato.wallpapers.ui.components.CuratoLoadingIndicator
import com.curato.wallpapers.ui.components.CuratoTopBar
import com.curato.wallpapers.ui.components.WallpaperCard
import com.curato.wallpapers.ui.theme.curatoColors

@Composable
fun TrendingScreen(
    onBack: () -> Unit,
    onWallpaperClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TrendingViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val listState = rememberLazyListState()

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
                    contentPadding = PaddingValues(bottom = 32.dp),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    item {
                        CuratoTopBar(
                            title = "Trending",
                            showBackButton = true,
                            onBack = onBack,
                            showSearch = false,
                        )
                    }

                    item { Spacer(Modifier.height(8.dp)) }

                    items(data.wallpapers.chunked(2)) { row ->
                        androidx.compose.foundation.layout.Row(
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            modifier = Modifier.padding(horizontal = 16.dp),
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
                        Spacer(Modifier.height(16.dp))
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
