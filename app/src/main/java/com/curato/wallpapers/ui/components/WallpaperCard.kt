package com.curato.wallpapers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.curato.wallpapers.domain.model.Wallpaper
import com.curato.wallpapers.ui.theme.InterFontFamily
import com.curato.wallpapers.ui.theme.OnSurfaceVariant
import com.curato.wallpapers.ui.theme.Primary
import com.curato.wallpapers.ui.theme.SurfaceContainerLow
import com.curato.wallpapers.ui.theme.WallpaperCardShape

/**
 * Standard 2-column grid card.
 * Image (3:4) → title + heart row underneath.
 * bg: SurfaceContainerLow (#1C1B1C), radius: 24dp
 */
@Composable
fun WallpaperCard(
    wallpaper: Wallpaper,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(WallpaperCardShape)
            .background(SurfaceContainerLow)
            .clickable(onClick = onClick),
    ) {
        // Wallpaper image — 3:4 aspect ratio
        AsyncImage(
            model = wallpaper.thumbnailUrl,
            contentDescription = wallpaper.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 4f),
        )

        // Title + Favorite row
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp, top = 8.dp, bottom = 8.dp, end = 4.dp),
        ) {
            Text(
                text = wallpaper.title.uppercase(),
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 10.sp,
                letterSpacing = (-0.5).sp,
                color = OnSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(1f),
            )
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier.size(32.dp),
            ) {
                Icon(
                    imageVector = if (wallpaper.isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                    contentDescription = if (wallpaper.isFavorite) "Remove favorite" else "Add favorite",
                    tint = if (wallpaper.isFavorite) Primary else OnSurfaceVariant,
                    modifier = Modifier.size(16.dp),
                )
            }
        }
    }
}
