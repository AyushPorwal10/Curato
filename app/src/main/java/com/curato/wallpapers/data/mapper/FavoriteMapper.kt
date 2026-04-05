package com.curato.wallpapers.data.mapper

import com.curato.wallpapers.data.local.entity.FavoriteEntity
import com.curato.wallpapers.domain.model.Wallpaper
import com.curato.wallpapers.domain.model.WallpaperCategory
import com.curato.wallpapers.domain.model.WallpaperSourceType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteMapper @Inject constructor() {

    fun toEntity(wallpaper: Wallpaper): FavoriteEntity = FavoriteEntity(
        id = wallpaper.id,
        title = wallpaper.title,
        description = wallpaper.description,
        photographerName = wallpaper.photographerName,
        photographerUrl = wallpaper.photographerUrl,
        thumbnailUrl = wallpaper.thumbnailUrl,
        previewUrl = wallpaper.previewUrl,
        fullUrl = wallpaper.fullUrl,
        dominantColor = wallpaper.dominantColor,
        width = wallpaper.width,
        height = wallpaper.height,
        category = wallpaper.category?.name,
        resolution = wallpaper.resolution,
        format = wallpaper.format,
        curatedBy = wallpaper.curatedBy,
    )

    fun toDomain(entity: FavoriteEntity): Wallpaper = Wallpaper(
        id = entity.id,
        title = entity.title,
        description = entity.description,
        photographerName = entity.photographerName,
        photographerUrl = entity.photographerUrl,
        thumbnailUrl = entity.thumbnailUrl,
        previewUrl = entity.previewUrl,
        fullUrl = entity.fullUrl,
        dominantColor = entity.dominantColor,
        width = entity.width,
        height = entity.height,
        category = entity.category?.let { runCatching { WallpaperCategory.valueOf(it) }.getOrNull() },
        resolution = entity.resolution,
        format = entity.format,
        curatedBy = entity.curatedBy,
        isFavorite = true,
        source = WallpaperSourceType.PEXELS,
    )
}
