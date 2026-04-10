package com.curato.wallpapers.data.wallpaper

import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import coil.imageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import com.curato.wallpapers.domain.common.Result
import com.curato.wallpapers.domain.common.safeCall
import com.curato.wallpapers.domain.model.Wallpaper
import com.curato.wallpapers.domain.model.WallpaperTarget
import com.curato.wallpapers.domain.wallpaper.WallpaperApplier
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class WallpaperApplierImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : WallpaperApplier {

    override suspend fun apply(wallpaper: Wallpaper, target: WallpaperTarget): Result<Unit> = safeCall {
        // Load full-resolution image via Coil (allowHardware=false → software bitmap
        // required by WallpaperManager.setBitmap)
        val request = ImageRequest.Builder(context)
            .data(wallpaper.fullUrl)
            .allowHardware(false)
            .build()

        val imageResult = context.imageLoader.execute(request)
        val bitmap = ((imageResult as? SuccessResult)?.drawable as? BitmapDrawable)?.bitmap
            ?: throw IllegalStateException("Failed to decode wallpaper image")

        val wm = WallpaperManager.getInstance(context)
        val flag = when (target) {
            WallpaperTarget.HOME_SCREEN -> WallpaperManager.FLAG_SYSTEM
            WallpaperTarget.LOCK_SCREEN -> WallpaperManager.FLAG_LOCK
            WallpaperTarget.BOTH -> WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
        }
        wm.setBitmap(bitmap, null, true, flag)
    }
}
