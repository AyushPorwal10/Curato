package com.curato.wallpapers.data.mapper

import com.curato.wallpapers.data.source.SourceType
import com.curato.wallpapers.data.source.SourceWallpaperDto
import com.curato.wallpapers.domain.model.Wallpaper
import com.curato.wallpapers.domain.model.WallpaperCategory
import com.curato.wallpapers.domain.model.WallpaperSourceType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WallpaperMapper @Inject constructor() {

    /**
     * Converts any [SourceWallpaperDto] to a domain [Wallpaper].
     *
     * [categoryOverride] lets a caller (e.g. the category handler when using Pexels)
     * pin a specific category when the source DTO does not carry one.
     * For Firebase, the category is already inside the DTO and no override is needed.
     */
    fun toDomain(
        dto: SourceWallpaperDto,
        categoryOverride: WallpaperCategory? = null,
    ): Wallpaper {
        val resolvedCategory = categoryOverride
            ?: dto.category?.let { name ->
                WallpaperCategory.entries.find { it.name == name }
            }

        return Wallpaper(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            photographerName = dto.authorName,
            photographerUrl = dto.authorUrl,
            thumbnailUrl = dto.thumbnailUrl,
            previewUrl = dto.previewUrl,
            fullUrl = dto.fullUrl,
            dominantColor = dto.dominantColor,
            width = dto.width,
            height = dto.height,
            category = resolvedCategory,
            tags = dto.tags,
            resolution = "${dto.width} × ${dto.height}",
            format = "JPEG",
            curatedBy = dto.authorName.ifBlank { "Curato Studio" },
            source = dto.sourceType.toDomainSourceType(),
        )
    }

    private fun SourceType.toDomainSourceType() = when (this) {
        SourceType.PEXELS -> WallpaperSourceType.PEXELS
        SourceType.FIREBASE -> WallpaperSourceType.FIREBASE
    }
}
