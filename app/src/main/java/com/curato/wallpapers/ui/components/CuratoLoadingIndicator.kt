package com.curato.wallpapers.ui.components

import android.graphics.Matrix
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.Morph
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.curato.wallpapers.ui.theme.curatoColors

/**
 * Shape-morphing loading indicator built on androidx.graphics.shapes — the same
 * stable Google library that Material3 Expressive uses internally for its LoadingIndicator.
 *
 * Cycles: circle → hexagon → rounded-square → triangle → octagon → (repeat)
 */
@Composable
fun CuratoLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = curatoColors.primary,
) {
    // All polygons use radius=1f centred at (0,0), so coordinates sit in [-1, 1]
    val shapes = remember {
        listOf(
            RoundedPolygon(numVertices = 12, rounding = CornerRounding(1f)), // circle
            RoundedPolygon(numVertices = 6),                                  // hexagon
            RoundedPolygon(numVertices = 4, rounding = CornerRounding(0.3f)), // rounded square
            RoundedPolygon(numVertices = 3, rounding = CornerRounding(0.2f)), // triangle
            RoundedPolygon(numVertices = 8, rounding = CornerRounding(0.1f)), // octagon
        )
    }

    val transition = rememberInfiniteTransition(label = "curato_loading")

    // Advances by 1.0 per shape; integer part = current shape index
    val rawProgress by transition.animateFloat(
        initialValue = 0f,
        targetValue = shapes.size.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = shapes.size * 700, easing = LinearEasing),
            repeatMode = RepeatMode.Restart,
        ),
        label = "shape",
    )

    // Slow continuous rotation
    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 3000, easing = LinearEasing),
        ),
        label = "rotation",
    )

    val currentIndex = rawProgress.toInt() % shapes.size
    val nextIndex = (currentIndex + 1) % shapes.size
    val morphProgress = rawProgress - rawProgress.toInt()

    val morph = remember(currentIndex, nextIndex) {
        Morph(shapes[currentIndex], shapes[nextIndex])
    }

    val nativePath = remember { android.graphics.Path() }
    val matrix = remember { Matrix() }

    Canvas(modifier = modifier.size(48.dp)) {
        morph.toPath(progress = morphProgress, path = nativePath)

        // Scale from [-1,1] to canvas coords, then rotate around centre
        val scale = size.minDimension * 0.45f
        matrix.apply {
            reset()
            postScale(scale, scale)
            postTranslate(size.width / 2f, size.height / 2f)
            postRotate(rotation, size.width / 2f, size.height / 2f)
        }
        nativePath.transform(matrix)

        drawPath(nativePath.asComposePath(), color = color)
    }
}

