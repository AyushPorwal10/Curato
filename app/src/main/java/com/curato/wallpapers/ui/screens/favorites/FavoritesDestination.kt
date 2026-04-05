package com.curato.wallpapers.ui.screens.favorites

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import com.curato.wallpapers.ui.navigation.AppDestinations
import com.curato.wallpapers.ui.navigation.TabDestination
import com.curato.wallpapers.ui.screens.explore.ExploreDestination

object FavoritesDestination : TabDestination {
    override val route = "favorites"
    override val order = 2
    override val label = "Favorites"
    override val icon: ImageVector = Icons.Rounded.FavoriteBorder
    override val selectedIcon: ImageVector = Icons.Rounded.Favorite

    @Composable
    override fun Screen(navController: NavController) {
        FavoritesScreen(
            onWallpaperClick = { id ->
                navController.navigate(AppDestinations.detailRoute(id))
            },
            onExploreClick = {
                navController.navigate(ExploreDestination.route) {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
        )
    }
}
