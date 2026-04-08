package com.curato.wallpapers.domain.common

import com.curato.wallpapers.domain.model.Wallpaper
import com.curato.wallpapers.domain.model.WallpaperCategory
import com.curato.wallpapers.domain.model.WallpaperTarget

/**
 * UDF: All possible user intents across the entire app.
 * ViewModels expose a dispatch(action: WallpaperAction) function.
 * Adding MVP2 features = just adding new sealed subclasses here.
 */
sealed class WallpaperAction {

    object LoadCurated : WallpaperAction()
    object LoadTrending : WallpaperAction()
    object LoadNextPage : WallpaperAction()

    data class Search(val query: String) : WallpaperAction()
    data class FilterByCategory(val category: WallpaperCategory) : WallpaperAction()
    object ClearFilter : WallpaperAction()
    object ClearSearch : WallpaperAction()

    data class LoadDetail(val id: String) : WallpaperAction()

    data class ToggleFavorite(val wallpaper: Wallpaper) : WallpaperAction()
    object LoadFavorites : WallpaperAction()

    data class ApplyWallpaper(
        val wallpaper: Wallpaper,
        val target: WallpaperTarget,
    ) : WallpaperAction()
    data class DownloadWallpaper(val wallpaper: Wallpaper) : WallpaperAction()

    data class Retry(val previousAction: WallpaperAction) : WallpaperAction()
}
