package com.curato.wallpapers.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/** 135° brand gradient — primary CTA buttons, hero headers */
val BrandGradient: Brush get() = Brush.linearGradient(
    colors = listOf(Color(0xFF7C4DFF), Color(0xFF009CCE)),
    start = Offset(0f, Float.POSITIVE_INFINITY),
    end = Offset(Float.POSITIVE_INFINITY, 0f),
)

/** Bottom-to-top dark scrim overlay on wallpaper cards */
val WallpaperScrimGradient: Brush get() = Brush.verticalGradient(
    colors = listOf(Color.Transparent, Color(0x66000000)),
)
