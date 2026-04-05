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
class SearchWallpaperHandler @Inject constructor(
    private val sourceFactory: WallpaperSourceFactory,
    private val mapper: WallpaperMapper,
) : WallpaperHandler<WallpaperRequest.Search, PaginatedResult<Wallpaper>> {

    override val handlerType = WallpaperRequest.Type.SEARCH

    override suspend fun handle(
        request: WallpaperRequest.Search,
    ): Result<PaginatedResult<Wallpaper>> = safeCall {
        val response = sourceFactory.getDefault().search(request.query, request.page, request.perPage)
        PaginatedResult(
            items = response.photos.map { mapper.toDomain(it) },
            currentPage = response.page,
            hasNextPage = response.nextPage != null,
            totalResults = response.totalResults,
        )
    }
}
