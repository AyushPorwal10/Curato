package com.curato.wallpapers.ui.navigation

import com.curato.wallpapers.ui.screens.explore.ExploreDestination
import com.curato.wallpapers.ui.screens.favorites.FavoritesDestination
import com.curato.wallpapers.ui.screens.home.HomeDestination

/**
 * Single source of truth for all navigation destinations.
 *
 * To add a tab in MVP2:
 *   1. Create YourDestination : TabDestination
 *   2. Append it to [tabs] — that's it.
 *
 * NavGraph and BottomBar are driven by [tabs] — they need zero changes.
 */
object AppDestinations {

    /** All tab destinations, ordered by [TabDestination.order] */
    val tabs: List<TabDestination> by lazy {
        listOf(
            HomeDestination,
            ExploreDestination,
            FavoritesDestination,
            // MVP2: ProfileDestination, CollectionsDestination, etc.
        ).sortedBy { it.order }
    }

    val startRoute: String get() = SPLASH_ROUTE

    // ── Non-tab routes ─────────────────────────────────────────────────────
    const val SPLASH_ROUTE  = "splash"
    const val DETAIL_ROUTE  = "detail/{wallpaperId}"
    fun detailRoute(wallpaperId: String) = "detail/$wallpaperId"

    const val TRENDING_ROUTE = "trending"
}
