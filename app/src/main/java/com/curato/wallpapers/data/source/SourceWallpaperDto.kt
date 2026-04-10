package com.curato.wallpapers.data.source

/**
 * Source-agnostic wallpaper data — every backend (Pexels, Firebase, …) maps its
 * own raw response into this before the mapper converts it to a domain [Wallpaper].
 *
 * Keeping this in the [data.source] package means handlers and mappers never
 * import anything Pexels- or Firebase-specific.
 */
data class SourceWallpaperDto(
    val id: String,
    val title: String,
    val description: String,
    /** Empty string for self-hosted content (Firebase). */
    val authorName: String,
    val authorUrl: String,
    val thumbnailUrl: String,   // ~400px  — grid cards
    val previewUrl: String,     // ~1080px — detail screen
    val fullUrl: String,        // original — apply / download
    val dominantColor: String,
    val width: Int,
    val height: Int,
    /** Raw category name matching [WallpaperCategory] enum name, e.g. "AMOLED". Null for Pexels. */
    val category: String?,
    val tags: List<String>,
    val sourceType: SourceType,
)

/**
 * One page of wallpapers returned by any source.
 * [totalResults] is -1 when the backend cannot cheaply report it (e.g. Firestore).
 */
data class SourceWallpapersPage(
    val wallpapers: List<SourceWallpaperDto>,
    val page: Int,
    val hasNextPage: Boolean,
    val totalResults: Int,
)
