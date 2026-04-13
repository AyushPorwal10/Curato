package com.curato.wallpapers.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.curato.wallpapers.ui.components.GlassmorphicBottomBar
import com.curato.wallpapers.ui.screens.detail.WallpaperDetailScreen
import com.curato.wallpapers.ui.screens.splash.SplashScreen
import com.curato.wallpapers.ui.screens.trending.TrendingScreen

/**
 * Root nav graph — driven entirely by [AppDestinations].
 *
 * Open/Closed: tab loop below never changes when new tabs are added.
 * Only non-tab screens (Detail, Onboarding, etc.) are added explicitly below.
 */
@Composable
fun CuratoNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    // Determine which routes show the bottom bar
    val tabRoutes = AppDestinations.tabs.map { it.route }.toSet()
    val showBottomBar = currentRoute in tabRoutes && currentRoute != AppDestinations.SPLASH_ROUTE

    Box(modifier = modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = AppDestinations.startRoute,
            modifier = Modifier.fillMaxSize(),
        ) {
            // ── Splash ────────────────────────────────────────────────────────
            composable(route = AppDestinations.SPLASH_ROUTE) {
                SplashScreen(
                    onSplashComplete = {
                        navController.navigate(AppDestinations.tabs.first().route) {
                            popUpTo(AppDestinations.SPLASH_ROUTE) { inclusive = true }
                        }
                    },
                )
            }

            // ── Tab screens — auto-registered, zero changes needed for new tabs ──
            AppDestinations.tabs.forEach { destination ->
                composable(route = destination.route) {
                    destination.Screen(navController = navController)
                }
            }

            // ── Non-tab screens ───────────────────────────────────────────────
            composable(route = AppDestinations.TRENDING_ROUTE) {
                TrendingScreen(
                    onBack = { navController.popBackStack() },
                    onWallpaperClick = { navController.navigate(AppDestinations.detailRoute(it)) },
                )
            }

            composable(
                route = AppDestinations.DETAIL_ROUTE,
                arguments = listOf(navArgument("wallpaperId") { type = NavType.StringType }),
            ) {
                WallpaperDetailScreen(
                    onBack = { navController.popBackStack() },
                )
            }
        }

        // Floating bottom bar — only on tab screens
        if (showBottomBar) {
            GlassmorphicBottomBar(
                destinations = AppDestinations.tabs,
                currentRoute = currentRoute,
                onDestinationSelected = { destination ->
                    navController.navigate(destination.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 24.dp),
            )
        }
    }
}
