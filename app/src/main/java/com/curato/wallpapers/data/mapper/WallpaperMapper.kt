package com.curato.wallpapers.data.mapper

import com.curato.wallpapers.data.remote.dto.PhotoDto
import com.curato.wallpapers.domain.model.Wallpaper
import com.curato.wallpapers.domain.model.WallpaperCategory
import com.curato.wallpapers.domain.model.WallpaperSourceType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WallpaperMapper @Inject constructor() {

    fun toDomain(dto: PhotoDto, category: WallpaperCategory? = null): Wallpaper = Wallpaper(
        id = dto.id.toString(),
        title = dto.alt.ifBlank { "Wallpaper #${dto.id}" },
        description = dto.alt,
        photographerName = dto.photographer,
        photographerUrl = dto.photographerUrl,
        thumbnailUrl = dto.src.small,
        previewUrl = dto.src.large,
        fullUrl = dto.src.portrait.ifBlank { dto.src.original },
        dominantColor = dto.avgColor,
        width = dto.width,
        height = dto.height,
        category = category,
        resolution = "${dto.width} × ${dto.height}",
        format = "JPEG",
        curatedBy = dto.photographer,
        source = WallpaperSourceType.PEXELS,
    )
}
