package com.curato.wallpapers.ui.components

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.curato.wallpapers.ui.theme.curatoColors

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun CuratoLoadingIndicator(
    modifier: Modifier = Modifier,
    color: Color = curatoColors.primary,
) {
    LoadingIndicator(
        modifier = modifier,
        color = color,
    )
}
