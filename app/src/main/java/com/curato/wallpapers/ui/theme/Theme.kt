package com.curato.wallpapers.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val CuratoDarkColorScheme = darkColorScheme(
    primary                = Color(0xFFCDBDFF),
    onPrimary              = Color(0xFF21005D),
    primaryContainer       = Color(0xFF7C4DFF),
    onPrimaryContainer     = Color(0xFFEADDFF),
    secondary              = Color(0xFF009CCE),
    onSecondary            = Color(0xFF003547),
    secondaryContainer     = Color(0xFF004D65),
    onSecondaryContainer   = Color(0xFFB8EAFF),
    background             = Color(0xFF131314),
    onBackground           = Color(0xFFE5E2E3),
    surface                = Color(0xFF131314),
    onSurface              = Color(0xFFE5E2E3),
    surfaceVariant         = Color(0xFF1C1B1C),
    onSurfaceVariant       = Color(0xFFCAC4CE),
    surfaceTint            = Color(0xFF7C4DFF),
    surfaceContainerLowest  = Color(0xFF0E0E0F),
    surfaceContainerLow    = Color(0xFF1C1B1C),
    surfaceContainer       = Color(0xFF201F20),
    surfaceContainerHigh   = Color(0xFF2A2A2B),
    surfaceContainerHighest = Color(0xFF353436),
    surfaceBright          = Color(0xFF3A383A),
    surfaceDim             = Color(0xFF131314),
    outline                = Color(0xFF494455),
    outlineVariant         = Color(0xFF494455),
    error                  = Color(0xFFCF6679),
    onError                = Color(0xFF690020),
    scrim                  = Color(0xFF000000),
    inverseSurface         = Color(0xFFE5E2E3),
    inverseOnSurface       = Color(0xFF131314),
    inversePrimary         = Color(0xFF7C4DFF),
)

private val CuratoLightColorScheme = lightColorScheme(
    primary                = Color(0xFF6750A4),
    onPrimary              = Color(0xFFFFFFFF),
    primaryContainer       = Color(0xFFEADDFF),
    onPrimaryContainer     = Color(0xFF21005D),
    secondary              = Color(0xFF006A86),
    onSecondary            = Color(0xFFFFFFFF),
    secondaryContainer     = Color(0xFFB8EAFF),
    onSecondaryContainer   = Color(0xFF001F2A),
    background             = Color(0xFFFEFBFF),
    onBackground           = Color(0xFF1C1B1F),
    surface                = Color(0xFFFEFBFF),
    onSurface              = Color(0xFF1C1B1F),
    surfaceVariant         = Color(0xFFE7E0EC),
    onSurfaceVariant       = Color(0xFF49454F),
    surfaceTint            = Color(0xFF6750A4),
    surfaceContainerLowest  = Color(0xFFFFFFFF),
    surfaceContainerLow    = Color(0xFFF5F2FF),
    surfaceContainer       = Color(0xFFEFEBFF),
    surfaceContainerHigh   = Color(0xFFE8E4F5),
    surfaceContainerHighest = Color(0xFFE2DEF0),
    surfaceBright          = Color(0xFFFFFFFF),
    surfaceDim             = Color(0xFFDED9EE),
    outline                = Color(0xFF79747E),
    outlineVariant         = Color(0xFFCAC4D0),
    error                  = Color(0xFFB3261E),
    onError                = Color(0xFFFFFFFF),
    scrim                  = Color(0xFF000000),
    inverseSurface         = Color(0xFF313033),
    inverseOnSurface       = Color(0xFFF4EFF4),
    inversePrimary         = Color(0xFFD0BCFF),
)

@Composable
fun CuratoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colorScheme = if (darkTheme) CuratoDarkColorScheme else CuratoLightColorScheme
    val colors = if (darkTheme) darkCuratoColors() else lightCuratoColors()

    CompositionLocalProvider(LocalCuratoColors provides colors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = CuratoTypography,
            shapes = CuratoShapes,
            content = content,
        )
    }
}
