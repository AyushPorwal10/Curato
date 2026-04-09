package com.curato.wallpapers.ui.screens.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Wallpaper
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curato.wallpapers.domain.model.WallpaperTarget
import com.curato.wallpapers.ui.theme.InterFontFamily
import com.curato.wallpapers.ui.theme.ManropeFontFamily
import com.curato.wallpapers.ui.theme.curatoColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyTargetSheet(
    sheetState: SheetState,
    onTargetSelected: (WallpaperTarget) -> Unit,
    onDismiss: () -> Unit,
) {
    val colors = curatoColors

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = colors.surfaceContainerLow,
        tonalElevation = 0.dp,
        shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp),
        ) {
            Text(
                text = "Set Wallpaper",
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                letterSpacing = (-0.5).sp,
                color = colors.onSurface,
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Choose where to apply",
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                color = colors.onSurfaceVariant,
            )
            Spacer(Modifier.height(24.dp))

            ApplyTargetRow(
                icon = Icons.Rounded.Home,
                label = "Home Screen",
                description = "Only the home screen",
                onClick = { onTargetSelected(WallpaperTarget.HOME_SCREEN) },
            )
            Spacer(Modifier.height(12.dp))
            ApplyTargetRow(
                icon = Icons.Rounded.Lock,
                label = "Lock Screen",
                description = "Only the lock screen",
                onClick = { onTargetSelected(WallpaperTarget.LOCK_SCREEN) },
            )
            Spacer(Modifier.height(12.dp))
            ApplyTargetRow(
                icon = Icons.Rounded.Wallpaper,
                label = "Both",
                description = "Home & lock screen",
                onClick = { onTargetSelected(WallpaperTarget.BOTH) },
            )
        }
    }
}

@Composable
private fun ApplyTargetRow(
    icon: ImageVector,
    label: String,
    description: String,
    onClick: () -> Unit,
) {
    val colors = curatoColors
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(colors.surfaceContainerHigh)
            .clickable(onClick = onClick)
            .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = colors.primary,
            modifier = Modifier.size(24.dp),
        )
        Spacer(Modifier.width(16.dp))
        Column {
            Text(
                text = label,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = colors.onSurface,
            )
            Text(
                text = description,
                fontFamily = InterFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = colors.onSurfaceVariant,
            )
        }
    }
}
