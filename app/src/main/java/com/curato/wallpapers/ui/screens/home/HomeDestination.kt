package com.curato.wallpapers.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.curato.wallpapers.ui.navigation.AppDestinations
import com.curato.wallpapers.ui.navigation.TabDestination

object HomeDestination : TabDestination {
    override val route = "home"
    override val order = 0
    override val label = "Home"
    override val icon: ImageVector = Icons.Rounded.Home

    @Composable
    override fun Screen(navController: NavController) {
        HomeScreen(
            onWallpaperClick = { id ->
                navController.navigate(AppDestinations.detailRoute(id))
            },
        )
    }
}
