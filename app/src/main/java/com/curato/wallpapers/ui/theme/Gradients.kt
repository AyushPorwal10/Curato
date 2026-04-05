package com.curato.wallpapers.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

/** 135° brand gradient — primary CTA buttons, hero headers */
val BrandGradient: Brush get() = Brush.linearGradient(
    colors = listOf(PrimaryContainer, SecondaryContainer),
    start = Offset(0f, Float.POSITIVE_INFINITY),
    end = Offset(Float.POSITIVE_INFINITY, 0f),
)

/** Bottom-to-top dark scrim overlay on wallpaper cards */
val WallpaperScrimGradient: Brush get() = Brush.verticalGradient(
    colors = listOf(Color.Transparent, Color(0xCC000000)),
)

/** Glassmorphism surface — used on bottom nav and overlays */
val GlassSurface = Color(0x99201F20)        // rgba(32,31,32,0.6)
val GlassBorder = Color(0x1AE5E2E3)         // rgba(229,226,227,0.1)
val GlassBorderLight = Color(0x33494455)    // rgba(73,68,85,0.2)
