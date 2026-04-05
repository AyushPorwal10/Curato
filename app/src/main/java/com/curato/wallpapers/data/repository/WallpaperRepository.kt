package com.curato.wallpapers.data.repository

import com.curato.wallpapers.domain.common.PaginatedResult
import com.curato.wallpapers.domain.common.Result
import com.curato.wallpapers.domain.model.Wallpaper
import com.curato.wallpapers.domain.model.WallpaperCategory

interface WallpaperRepository {
    suspend fun getCurated(page: Int = 1, perPage: Int = 15): Result<PaginatedResult<Wallpaper>>
    suspend fun search(query: String, page: Int = 1, perPage: Int = 15): Result<PaginatedResult<Wallpaper>>
    suspend fun getByCategory(category: WallpaperCategory, page: Int = 1, perPage: Int = 15): Result<PaginatedResult<Wallpaper>>
    suspend fun getById(id: String): Result<Wallpaper>
}
