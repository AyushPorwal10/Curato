package com.curato.wallpapers.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController

/**
 * Contract every tab screen must implement.
 *
 * Open/Closed Principle:
 *   - NavGraph iterates over AppDestinations.tabs → closed for modification
 *   - GlassmorphicBottomBar reads the same list → closed for modification
 *   - Adding MVP2 tab = implement this interface + append to AppDestinations.tabs → open for extension
 *
 * No existing file needs to change when a new tab is added.
 */
interface TabDestination {
    /** Unique navigation route string */
    val route: String

    /** Position in the bottom bar — determines display order */
    val order: Int

    /** Bottom bar label */
    val label: String

    /** Icon shown when this tab is NOT selected */
    val icon: ImageVector

    /** Icon shown when this tab IS selected (defaults to [icon]) */
    val selectedIcon: ImageVector get() = icon

    /**
     * The full screen composable for this tab.
     * Called by NavGraph inside a composable { } block — Hilt ViewModels work here.
     */
    @Composable
    fun Screen(navController: NavController)
}
