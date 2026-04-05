package com.curato.wallpapers.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curato.wallpapers.ui.theme.ManropeFontFamily
import com.curato.wallpapers.ui.theme.OnSurface
import com.curato.wallpapers.ui.theme.OnSurfaceVariant
import com.curato.wallpapers.ui.theme.SurfaceBase

/**
 * Standard top app bar — app name + search icon.
 * Height: 64dp, horizontal padding: 24dp.
 * bg: SurfaceBase (#131314) to match Figma.
 */
@Composable
fun CuratoTopBar(
    title: String,
    modifier: Modifier = Modifier,
    showBackButton: Boolean = false,
    onBack: (() -> Unit)? = null,
    showSearch: Boolean = true,
    onSearch: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (showBackButton && onBack != null) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = "Back",
                        tint = OnSurface,
                        modifier = Modifier.size(20.dp),
                    )
                }
            }
            Text(
                text = title,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                letterSpacing = (-1).sp,
                color = OnSurface,
            )
        }
        if (showSearch) {
            IconButton(onClick = { onSearch?.invoke() }) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Search",
                    tint = OnSurfaceVariant,
                    modifier = Modifier.size(22.dp),
                )
            }
        }
    }
}
