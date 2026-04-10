package com.curato.wallpapers.ui.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import com.curato.wallpapers.ui.theme.curatoColors

/**
 * Returns an animated linear-gradient [Brush] that produces a left-to-right
 * shimmer sweep. Uses design-system surface tokens so it matches both light
 * and dark themes without any extra configuration.
 *
 * Intended use: pass as the `modifier = Modifier.background(rememberShimmerBrush())`
 * inside a loading-state composable.
 */
@Composable
fun rememberShimmerBrush(): Brush {
    val colors = curatoColors

    val shimmerColors = listOf(
        colors.surfaceContainerLow,
        colors.surfaceContainerHigh,
        colors.surfaceBright,
        colors.surfaceContainerHigh,
        colors.surfaceContainerLow,
    )

    val transition = rememberInfiniteTransition(label = "shimmer")
    val translateX by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1600f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1400, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shimmer_x",
    )

    // The gradient window is 800px wide; it sweeps fully across 1600px travel
    // so the highlight passes through every point on typical phone screens.
    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset(x = translateX - 800f, y = 0f),
        end = Offset(x = translateX, y = 0f),
    )
}
