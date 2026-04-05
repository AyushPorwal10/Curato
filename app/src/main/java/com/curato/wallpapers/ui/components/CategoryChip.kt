package com.curato.wallpapers.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curato.wallpapers.domain.model.WallpaperCategory
import com.curato.wallpapers.ui.theme.InterFontFamily
import com.curato.wallpapers.ui.theme.OnSurfaceVariant
import com.curato.wallpapers.ui.theme.Primary
import com.curato.wallpapers.ui.theme.SurfaceContainerHigh

private val ChipShape = RoundedCornerShape(percent = 50)

// Selected chip bg is Primary (#CDBDFF), text is deep purple
private val SelectedChipBg = Primary
private val SelectedChipText = androidx.compose.ui.graphics.Color(0xFF370096)

/**
 * Filter chip for wallpaper categories.
 * Selected: Primary fill + dark text + 1.05x scale spring.
 * Unselected: SurfaceContainerHigh fill + OnSurfaceVariant text.
 */
@Composable
fun CategoryChip(
    category: WallpaperCategory,
    selected: Boolean,
    onSelect: (WallpaperCategory) -> Unit,
    modifier: Modifier = Modifier,
) {
    val bg by animateColorAsState(
        targetValue = if (selected) SelectedChipBg else SurfaceContainerHigh,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "chip_bg",
    )
    val textColor by animateColorAsState(
        targetValue = if (selected) SelectedChipText else OnSurfaceVariant,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow),
        label = "chip_text",
    )
    val scale by animateFloatAsState(
        targetValue = if (selected) 1.05f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium,
        ),
        label = "chip_scale",
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .scale(scale)
            .clip(ChipShape)
            .background(bg)
            .clickable { onSelect(category) }
            .padding(horizontal = 20.dp, vertical = 10.dp),
    ) {
        Text(
            text = category.displayName,
            fontFamily = InterFontFamily,
            fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Medium,
            fontSize = 14.sp,
            color = textColor,
        )
    }
}
