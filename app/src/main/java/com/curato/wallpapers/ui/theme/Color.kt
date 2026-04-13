package com.curato.wallpapers.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// ── Dark palette ──────────────────────────────────────────────────────────────
private val Dark_SurfaceBase             = Color(0xFF131314)
private val Dark_SurfaceContainerLowest  = Color(0xFF0E0E0F)
private val Dark_SurfaceContainerLow     = Color(0xFF1C1B1C)
private val Dark_SurfaceContainer        = Color(0xFF201F20)
private val Dark_SurfaceContainerHigh    = Color(0xFF2A2A2B)
private val Dark_SurfaceContainerHighest = Color(0xFF353436)
private val Dark_SurfaceBright           = Color(0xFF3A383A)
private val Dark_SurfaceDim              = Color(0xFF131314)
private val Dark_OnSurface               = Color(0xFFE5E2E3)
private val Dark_OnSurfaceVariant        = Color(0xFFCAC4CE)
private val Dark_OutlineVariant          = Color(0xFF494455)
private val Dark_Primary                 = Color(0xFFCDBDFF)
private val Dark_OnPrimary               = Color(0xFF21005D)
private val Dark_PrimaryContainer        = Color(0xFF7C4DFF)
private val Dark_SecondaryContainer      = Color(0xFF009CCE)
private val Dark_GlassSurface            = Color(0x55201F20)
private val Dark_GlassBorder             = Color(0x40E5E2E3)
private val Dark_GlassBorderLight        = Color(0x55494455)

// ── Light palette ─────────────────────────────────────────────────────────────
private val Light_SurfaceBase             = Color(0xFFFEFBFF)
private val Light_SurfaceContainerLowest  = Color(0xFFFFFFFF)
private val Light_SurfaceContainerLow     = Color(0xFFF5F2FF)
private val Light_SurfaceContainer        = Color(0xFFEFEBFF)
private val Light_SurfaceContainerHigh    = Color(0xFFE8E4F5)
private val Light_SurfaceContainerHighest = Color(0xFFE2DEF0)
private val Light_SurfaceBright           = Color(0xFFFFFFFF)
private val Light_SurfaceDim              = Color(0xFFDED9EE)
private val Light_OnSurface               = Color(0xFF1C1B1F)
private val Light_OnSurfaceVariant        = Color(0xFF49454F)
private val Light_OutlineVariant          = Color(0xFFCAC4D0)
private val Light_Primary                 = Color(0xFF6750A4)
private val Light_OnPrimary               = Color(0xFFFFFFFF)
private val Light_PrimaryContainer        = Color(0xFF7C4DFF)
private val Light_SecondaryContainer      = Color(0xFF009CCE)
private val Light_GlassSurface            = Color(0x70FFFFFF)
private val Light_GlassBorder             = Color(0x55FFFFFF)
private val Light_GlassBorderLight        = Color(0x55CAC4D0)

// ── Semantic color container ──────────────────────────────────────────────────
data class CuratoColors(
    val surfaceBase: Color,
    val surfaceContainerLowest: Color,
    val surfaceContainerLow: Color,
    val surfaceContainer: Color,
    val surfaceContainerHigh: Color,
    val surfaceContainerHighest: Color,
    val surfaceBright: Color,
    val surfaceDim: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val outlineVariant: Color,
    val primary: Color,
    val onPrimary: Color,
    val primaryContainer: Color,
    val secondaryContainer: Color,
    val glassSurface: Color,
    val glassBorder: Color,
    val glassBorderLight: Color,
    val isDark: Boolean,
)

fun darkCuratoColors() = CuratoColors(
    surfaceBase             = Dark_SurfaceBase,
    surfaceContainerLowest  = Dark_SurfaceContainerLowest,
    surfaceContainerLow     = Dark_SurfaceContainerLow,
    surfaceContainer        = Dark_SurfaceContainer,
    surfaceContainerHigh    = Dark_SurfaceContainerHigh,
    surfaceContainerHighest = Dark_SurfaceContainerHighest,
    surfaceBright           = Dark_SurfaceBright,
    surfaceDim              = Dark_SurfaceDim,
    onSurface               = Dark_OnSurface,
    onSurfaceVariant        = Dark_OnSurfaceVariant,
    outlineVariant          = Dark_OutlineVariant,
    primary                 = Dark_Primary,
    onPrimary               = Dark_OnPrimary,
    primaryContainer        = Dark_PrimaryContainer,
    secondaryContainer      = Dark_SecondaryContainer,
    glassSurface            = Dark_GlassSurface,
    glassBorder             = Dark_GlassBorder,
    glassBorderLight        = Dark_GlassBorderLight,
    isDark                  = true,
)

fun lightCuratoColors() = CuratoColors(
    surfaceBase             = Light_SurfaceBase,
    surfaceContainerLowest  = Light_SurfaceContainerLowest,
    surfaceContainerLow     = Light_SurfaceContainerLow,
    surfaceContainer        = Light_SurfaceContainer,
    surfaceContainerHigh    = Light_SurfaceContainerHigh,
    surfaceContainerHighest = Light_SurfaceContainerHighest,
    surfaceBright           = Light_SurfaceBright,
    surfaceDim              = Light_SurfaceDim,
    onSurface               = Light_OnSurface,
    onSurfaceVariant        = Light_OnSurfaceVariant,
    outlineVariant          = Light_OutlineVariant,
    primary                 = Light_Primary,
    onPrimary               = Light_OnPrimary,
    primaryContainer        = Light_PrimaryContainer,
    secondaryContainer      = Light_SecondaryContainer,
    glassSurface            = Light_GlassSurface,
    glassBorder             = Light_GlassBorder,
    glassBorderLight        = Light_GlassBorderLight,
    isDark                  = false,
)

val LocalCuratoColors = staticCompositionLocalOf { darkCuratoColors() }

val curatoColors: CuratoColors
    @Composable get() = LocalCuratoColors.current
