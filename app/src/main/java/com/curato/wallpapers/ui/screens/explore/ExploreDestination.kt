package com.curato.wallpapers.ui.screens.explore

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Explore
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.curato.wallpapers.ui.navigation.AppDestinations
import com.curato.wallpapers.ui.navigation.TabDestination

object ExploreDestination : TabDestination {
    override val route = "explore"
    override val order = 1
    override val label = "Explore"
    override val icon: ImageVector = Icons.Rounded.Explore

    @Composable
    override fun Screen(navController: NavController) {
        ExploreScreen(
            onWallpaperClick = { id ->
                navController.navigate(AppDestinations.detailRoute(id))
            },
        )
    }
}
