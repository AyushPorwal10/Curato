package com.curato.wallpapers.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val CuratoShapes = Shapes(
    // Small UI elements — chips, tags
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    // Cards and containers — xl: 24dp
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    // Modals, bottom sheets
    extraLarge = RoundedCornerShape(28.dp),
)

// Full pill — for primary buttons
val FullRoundedShape = RoundedCornerShape(percent = 50)

// Wallpaper card 9:16 container
val WallpaperCardShape = RoundedCornerShape(24.dp)

// Bottom sheet / modals
val BottomSheetShape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp)
