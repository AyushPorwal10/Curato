package com.curato.wallpapers.domain.wallpaper

import com.curato.wallpapers.domain.common.Result
import com.curato.wallpapers.domain.model.Wallpaper
import com.curato.wallpapers.domain.model.WallpaperTarget

/**
 * Contract for applying a wallpaper to the device.
 * Any screen/feature that needs to apply wallpapers depends only on this interface.
 */
interface WallpaperApplier {
    suspend fun apply(wallpaper: Wallpaper, target: WallpaperTarget): Result<Unit>
}
