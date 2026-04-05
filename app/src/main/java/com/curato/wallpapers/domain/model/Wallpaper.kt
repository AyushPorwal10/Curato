package com.curato.wallpapers.domain.model

data class Wallpaper(
    val id: String,
    val title: String,
    val description: String,
    val photographerName: String,
    val photographerUrl: String,
    // URLs — different quality tiers for loading strategy
    val thumbnailUrl: String,   // small — for grid cards
    val previewUrl: String,     // large — for detail screen
    val fullUrl: String,        // original — for download/apply
    val dominantColor: String,
    val width: Int,
    val height: Int,
    val category: WallpaperCategory?,
    val tags: List<String> = emptyList(),
    val resolution: String = "${width} × ${height}",
    val format: String = "JPEG",
    val curatedBy: String = "Curato Studio",
    val isFavorite: Boolean = false,
    val source: WallpaperSourceType = WallpaperSourceType.PEXELS,
)

enum class WallpaperSourceType { PEXELS, UNSPLASH, AI_GENERATED }

enum class WallpaperTarget { HOME_SCREEN, LOCK_SCREEN, BOTH }
