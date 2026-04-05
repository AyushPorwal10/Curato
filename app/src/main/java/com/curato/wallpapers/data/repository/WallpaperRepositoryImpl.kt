package com.curato.wallpapers.data.repository

import com.curato.wallpapers.data.handler.WallpaperHandlerFactory
import com.curato.wallpapers.data.handler.WallpaperRequest
import com.curato.wallpapers.domain.common.PaginatedResult
import com.curato.wallpapers.domain.common.Result
import com.curato.wallpapers.domain.model.Wallpaper
import com.curato.wallpapers.domain.model.WallpaperCategory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository delegates to the HandlerFactory.
 * It never knows which source is active — that's the Strategy's job.
 * Adding a new operation in MVP2: just call handlerFactory.getHandler(NEW_TYPE).handle(request).
 */
@Singleton
class WallpaperRepositoryImpl @Inject constructor(
    private val handlerFactory: WallpaperHandlerFactory,
) : WallpaperRepository {

    override suspend fun getCurated(page: Int, perPage: Int): Result<PaginatedResult<Wallpaper>> =
        handlerFactory
            .getHandler<WallpaperRequest.GetCurated, PaginatedResult<Wallpaper>>(WallpaperRequest.Type.CURATED)
            .handle(WallpaperRequest.GetCurated(page, perPage))

    override suspend fun search(query: String, page: Int, perPage: Int): Result<PaginatedResult<Wallpaper>> =
        handlerFactory
            .getHandler<WallpaperRequest.Search, PaginatedResult<Wallpaper>>(WallpaperRequest.Type.SEARCH)
            .handle(WallpaperRequest.Search(query, page, perPage))

    override suspend fun getByCategory(category: WallpaperCategory, page: Int, perPage: Int): Result<PaginatedResult<Wallpaper>> =
        handlerFactory
            .getHandler<WallpaperRequest.GetByCategory, PaginatedResult<Wallpaper>>(WallpaperRequest.Type.CATEGORY)
            .handle(WallpaperRequest.GetByCategory(category, page, perPage))

    override suspend fun getById(id: String): Result<Wallpaper> =
        handlerFactory
            .getHandler<WallpaperRequest.GetDetail, Wallpaper>(WallpaperRequest.Type.DETAIL)
            .handle(WallpaperRequest.GetDetail(id))
}
