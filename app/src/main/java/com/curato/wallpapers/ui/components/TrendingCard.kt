package com.curato.wallpapers.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.curato.wallpapers.domain.model.Wallpaper
import com.curato.wallpapers.ui.theme.InterFontFamily
import com.curato.wallpapers.ui.theme.WallpaperCardShape
import com.curato.wallpapers.ui.theme.WallpaperScrimGradient
import com.curato.wallpapers.ui.theme.curatoColors

@Composable
fun TrendingCard(
    wallpaper: Wallpaper,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = curatoColors
    Box(
        modifier = modifier
            .width(280.dp)
            .aspectRatio(2f / 3f)
            .clip(WallpaperCardShape)
            .clickable(onClick = onClick),
    ) {
        AsyncImage(
            model = wallpaper.previewUrl,
            contentDescription = wallpaper.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
        )

        Box(
            modifier = Modifier
                .matchParentSize()
                .background(WallpaperScrimGradient),
        )

        wallpaper.category?.let { category ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 16.dp, bottom = 12.dp)
                    .clip(RoundedCornerShape(percent = 50))
                    .background(colors.glassSurface)
                    .border(0.5.dp, colors.glassBorder, RoundedCornerShape(percent = 50))
                    .padding(horizontal = 14.dp, vertical = 6.dp),
            ) {
                Text(
                    text = category.displayName,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = colors.onSurface,
                )
            }
        }
    }
}
