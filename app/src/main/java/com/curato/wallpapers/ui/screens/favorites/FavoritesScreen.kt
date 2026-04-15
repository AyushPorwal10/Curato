package com.curato.wallpapers.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.curato.wallpapers.domain.common.WallpaperAction
import com.curato.wallpapers.ui.components.CuratoTopBar
import com.curato.wallpapers.ui.components.WallpaperCard
import com.curato.wallpapers.ui.theme.ManropeFontFamily
import com.curato.wallpapers.ui.theme.curatoColors

@Composable
fun FavoritesScreen(
    onWallpaperClick: (String) -> Unit,
    onExploreClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
) {
    val uiData by viewModel.uiState.collectAsStateWithLifecycle()
    val colors = curatoColors

    Column(modifier = modifier.fillMaxSize()) {
        CuratoTopBar(title = "Favorites", showSearch = false)

        if (uiData.favorites.isEmpty()) {
            // Empty state — matches Figma design
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 40.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Placeholder illustration (Figma shows a blurred sphere card)
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(200.dp)
                            .padding(16.dp),
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = null,
                            tint = colors.onSurfaceVariant.copy(alpha = 0.3f),
                            modifier = Modifier.size(80.dp),
                        )
                    }

                    Text(
                        text = "No favorites yet",
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = colors.onSurface,
                        textAlign = TextAlign.Center,
                    )

                    Text(
                        text = "Start curating your personal gallery.\nTap the heart icon on any wallpaper\nto see it here.",
                        fontFamily = com.curato.wallpapers.ui.theme.InterFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = colors.onSurfaceVariant,
                        textAlign = TextAlign.Center,
                        lineHeight = 22.sp,
                    )

                    Spacer(Modifier.height(8.dp))

                    TextButton(onClick = onExploreClick) {
                        Text(
                            text = "Explore Gallery",
                            fontFamily = com.curato.wallpapers.ui.theme.InterFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 15.sp,
                            color = colors.primary,
                        )
                    }
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 100.dp,
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(uiData.favorites.chunked(2)) { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        row.forEach { wallpaper ->
                            WallpaperCard(
                                wallpaper = wallpaper.copy(isFavorite = true),
                                onClick = { onWallpaperClick(wallpaper.id) },
                                onFavoriteClick = {
                                    viewModel.dispatch(WallpaperAction.ToggleFavorite(wallpaper))
                                },
                                modifier = Modifier.weight(1f),
                            )
                        }
                        if (row.size == 1) Spacer(Modifier.weight(1f))
                    }
                }
            }
        }
    }
}
