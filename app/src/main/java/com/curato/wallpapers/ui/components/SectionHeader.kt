package com.curato.wallpapers.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.curato.wallpapers.ui.theme.InterFontFamily
import com.curato.wallpapers.ui.theme.ManropeFontFamily
import com.curato.wallpapers.ui.theme.OnSurface
import com.curato.wallpapers.ui.theme.OnSurfaceVariant
import com.curato.wallpapers.ui.theme.Primary

/**
 * Section header — eyebrow label + bold title + optional "See all" action.
 * Matches "CURATED SELECTION / Trending / See all" pattern from Figma.
 */
@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    eyebrow: String? = null,
    subtitle: String? = null,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
            eyebrow?.let {
                Text(
                    text = it.uppercase(),
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    letterSpacing = 1.2.sp,
                    color = Primary,
                )
            }
            Text(
                text = title,
                fontFamily = ManropeFontFamily,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 28.sp,
                letterSpacing = (-0.75).sp,
                color = OnSurface,
            )
            subtitle?.let {
                Text(
                    text = it,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    color = OnSurfaceVariant,
                )
            }
        }
        if (actionLabel != null && onAction != null) {
            TextButton(onClick = onAction) {
                Text(
                    text = actionLabel,
                    fontFamily = InterFontFamily,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 14.sp,
                    color = Primary,
                )
            }
        }
    }
}
