package com.curato.wallpapers.data.handler.impl

import com.curato.wallpapers.data.handler.WallpaperHandler
import com.curato.wallpapers.data.handler.WallpaperRequest
import com.curato.wallpapers.data.mapper.WallpaperMapper
import com.curato.wallpapers.data.source.WallpaperSourceFactory
import com.curato.wallpapers.domain.common.PaginatedResult
import com.curato.wallpapers.domain.common.Result
import com.curato.wallpapers.domain.common.safeCall
import com.curato.wallpapers.domain.model.Wallpaper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryWallpaperHandler @Inject constructor(
    private val sourceFactory: WallpaperSourceFactory,
    private val mapper: WallpaperMapper,
) : WallpaperHandler<WallpaperRequest.GetByCategory, PaginatedResult<Wallpaper>> {

    override val handlerType = WallpaperRequest.Type.CATEGORY

    override suspend fun handle(
        request: WallpaperRequest.GetByCategory,
    ): Result<PaginatedResult<Wallpaper>> = safeCall {
        val page = sourceFactory.getDefault().getByCategory(
            category = request.category,
            page = request.page,
            perPage = request.perPage,
        )
        PaginatedResult(
            items = page.wallpapers.map { mapper.toDomain(it, categoryOverride = request.category) },
            currentPage = page.page,
            hasNextPage = page.hasNextPage,
            totalResults = page.totalResults,
        )
    }
}
