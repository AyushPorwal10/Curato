package com.curato.wallpapers.domain.wallpaper

import com.curato.wallpapers.domain.common.Result
import com.curato.wallpapers.domain.model.Wallpaper

interface WallpaperDownloader {
    suspend fun download(wallpaper: Wallpaper): Result<Unit>
}
