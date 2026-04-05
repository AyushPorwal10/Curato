package com.curato.wallpapers.data.source

import com.curato.wallpapers.data.remote.dto.PhotoDto
import com.curato.wallpapers.data.remote.dto.PexelsPhotosResponse

/**
 * Strategy interface — every wallpaper backend (Pexels, Unsplash, AI) implements this.
 * The rest of the data layer talks only to this interface, never to concrete classes.
 */
interface WallpaperSource {

    val sourceType: SourceType

    suspend fun getCurated(page: Int, perPage: Int): PexelsPhotosResponse

    suspend fun search(
        query: String,
        page: Int,
        perPage: Int,
    ): PexelsPhotosResponse

    suspend fun getById(id: String): PhotoDto
}
