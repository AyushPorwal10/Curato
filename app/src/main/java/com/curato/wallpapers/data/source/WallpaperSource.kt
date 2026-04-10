package com.curato.wallpapers.data.source

import com.curato.wallpapers.domain.model.WallpaperCategory

/**
 * Strategy interface — every wallpaper backend (Pexels, Firebase, …) implements this.
 * No Pexels or Firebase types leak past this boundary.
 */
interface WallpaperSource {

    val sourceType: SourceType

    suspend fun getCurated(page: Int, perPage: Int): SourceWallpapersPage

    suspend fun search(query: String, page: Int, perPage: Int): SourceWallpapersPage

    /**
     * Fetch wallpapers for a specific category.
     * Separated from [search] so each backend can use its most efficient query
     * (Pexels: keyword search; Firebase: equality filter on the category field).
     */
    suspend fun getByCategory(
        category: WallpaperCategory,
        page: Int,
        perPage: Int,
    ): SourceWallpapersPage

    suspend fun getById(id: String): SourceWallpaperDto
}
