package com.curato.wallpapers.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val CuratoDarkColorScheme = darkColorScheme(
    // Brand
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryContainer,
    onPrimaryContainer = Color(0xFFEADDFF),

    secondary = SecondaryContainer,
    onSecondary = Color(0xFF003547),
    secondaryContainer = Color(0xFF004D65),
    onSecondaryContainer = Color(0xFFB8EAFF),

    // Surfaces — AMOLED hierarchy
    background = SurfaceBase,
    onBackground = OnSurface,
    surface = SurfaceBase,
    onSurface = OnSurface,
    surfaceVariant = SurfaceContainerLow,
    onSurfaceVariant = OnSurfaceVariant,
    surfaceTint = PrimaryContainer,

    // Containers
    surfaceContainerLowest = SurfaceContainerLowest,
    surfaceContainerLow = SurfaceContainerLow,
    surfaceContainer = SurfaceContainer,
    surfaceContainerHigh = SurfaceContainerHigh,
    surfaceContainerHighest = SurfaceContainerHighest,
    surfaceBright = SurfaceBright,
    surfaceDim = SurfaceDim,

    // Outline
    outline = OutlineVariant,
    outlineVariant = OutlineVariant,

    // Error
    error = Error,
    onError = OnError,

    scrim = Scrim,
    inverseSurface = OnSurface,
    inverseOnSurface = SurfaceBase,
    inversePrimary = PrimaryContainer,
)

@Composable
fun CuratoTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = CuratoDarkColorScheme,
        typography = CuratoTypography,
        shapes = CuratoShapes,
        content = content,
    )
}
