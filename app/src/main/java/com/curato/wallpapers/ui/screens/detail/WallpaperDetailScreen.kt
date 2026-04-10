package com.curato.wallpapers.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Download
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.IosShare
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Wallpaper
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.curato.wallpapers.domain.common.UiState
import com.curato.wallpapers.domain.common.WallpaperAction
import com.curato.wallpapers.ui.components.CuratoGradientButton
import com.curato.wallpapers.ui.components.GlassmorphicIconButton
import com.curato.wallpapers.ui.theme.InterFontFamily
import com.curato.wallpapers.ui.theme.ManropeFontFamily
import com.curato.wallpapers.ui.theme.curatoColors

@Composable
fun WallpaperDetailScreen(
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WallpaperDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val colors = curatoColors
    Box(modifier = modifier.fillMaxSize()) {
        when (val state = uiState) {
            is UiState.Loading -> CircularProgressIndicator(
                color = colors.primary,
                modifier = Modifier.align(Alignment.Center),
            )

            is UiState.Error -> Text(
                text = state.message,
                color = colors.onSurface,
                modifier = Modifier.align(Alignment.Center),
            )

            is UiState.Success -> {
                val data = state.data
                val wallpaper = data.wallpaper

                // Full-screen wallpaper background
                AsyncImage(
                    model = wallpaper.previewUrl,
                    contentDescription = wallpaper.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                )

                // Top action bar — back / share / info
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                ) {
                    GlassmorphicIconButton(
                        icon = Icons.Rounded.KeyboardArrowDown,
                        contentDescription = "Back",
                        onClick = onBack,
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        GlassmorphicIconButton(
                            icon = Icons.Rounded.IosShare,
                            contentDescription = "Share",
                            onClick = { /* TODO */ },
                        )
                    }
                }

                // Bottom info sheet — glassmorphic
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .clip(RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp))
                        .background(colors.glassSurface)
                        .border(
                            0.5.dp,
                            colors.glassBorder,
                            RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
                        )
                        .navigationBarsPadding()
                        .padding(horizontal = 24.dp, vertical = 24.dp),
                ) {
                    // Category chip
                    wallpaper.category?.let { category ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(percent = 50))
                                .background(Color(0x33CDBDFF))
                                .padding(horizontal = 12.dp, vertical = 5.dp),
                        ) {
                            Text(
                                text = category.displayName.uppercase(),
                                fontFamily = InterFontFamily,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp,
                                letterSpacing = 1.sp,
                                color = colors.primary,
                            )
                        }
                        Spacer(Modifier.height(10.dp))
                    }

                    // Title
                    Text(
                        text = wallpaper.title,
                        fontFamily = ManropeFontFamily,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 32.sp,
                        letterSpacing = (-0.8).sp,
                        color = colors.onSurface,
                        lineHeight = 38.sp,
                    )

                    Spacer(Modifier.height(8.dp))

                    // Description
                    Text(
                        text = wallpaper.description,
                        fontFamily = InterFontFamily,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = colors.onSurfaceVariant,
                        lineHeight = 22.sp,
                    )

                    Spacer(Modifier.height(20.dp))

                    // Action row: Favorite | Download | Apply
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        // Favorite icon button
                        IconButton(onClick = {
                            viewModel.dispatch(WallpaperAction.ToggleFavorite(wallpaper))
                        }) {
                            Icon(
                                imageVector = if (data.isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                                contentDescription = "Favorite",
                                tint = if (data.isFavorite) colors.primary else colors.onSurface,
                                modifier = Modifier.size(24.dp),
                            )
                        }

                        Spacer(Modifier.width(4.dp))

                        // Download icon button
                        IconButton(onClick = { /* TODO */ }) {
                            Icon(
                                imageVector = Icons.Rounded.Download,
                                contentDescription = "Download",
                                tint = colors.onSurface,
                                modifier = Modifier.size(24.dp),
                            )
                        }

                        Spacer(Modifier.width(12.dp))

                        // Apply gradient button — fills remaining space
                        CuratoGradientButton(
                            text = "Apply",
                            icon = Icons.Rounded.Wallpaper,
                            onClick = { /* TODO: WallpaperManager */ },
                            modifier = Modifier.weight(1f),
                        )
                    }

                    Spacer(Modifier.height(16.dp))

                    // Metadata row
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        MetadataItem(label = "RESOLUTION", value = wallpaper.resolution)
                        MetadataItem(label = "FORMAT", value = wallpaper.format)
                        MetadataItem(label = "CURATED BY", value = wallpaper.curatedBy)
                    }
                }
            }

            else -> Unit
        }
    }
}

@Composable
private fun MetadataItem(label: String, value: String) {
    val colors = curatoColors
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            text = label,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            letterSpacing = 0.8.sp,
            color = colors.onSurfaceVariant.copy(alpha = 0.6f),
        )
        Text(
            text = value,
            fontFamily = InterFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = colors.onSurface,
        )
    }
}
