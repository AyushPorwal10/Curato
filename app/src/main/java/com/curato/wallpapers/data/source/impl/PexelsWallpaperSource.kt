package com.curato.wallpapers.data.source.impl

import com.curato.wallpapers.data.remote.api.PexelsApi
import com.curato.wallpapers.data.remote.dto.PhotoDto
import com.curato.wallpapers.data.source.SourceType
import com.curato.wallpapers.data.source.SourceWallpaperDto
import com.curato.wallpapers.data.source.SourceWallpapersPage
import com.curato.wallpapers.data.source.WallpaperSource
import com.curato.wallpapers.domain.model.WallpaperCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PexelsWallpaperSource @Inject constructor(
    private val api: PexelsApi,
) : WallpaperSource {

    override val sourceType = SourceType.PEXELS

    override suspend fun getCurated(page: Int, perPage: Int): SourceWallpapersPage {
        val response = api.getCuratedPhotos(page, perPage)
        return SourceWallpapersPage(
            wallpapers = response.photos.map { it.toSourceDto() },
            page = response.page,
            hasNextPage = response.nextPage != null,
            totalResults = response.totalResults,
        )
    }

    override suspend fun search(query: String, page: Int, perPage: Int): SourceWallpapersPage {
        val response = api.searchPhotos(query, page, perPage)
        return SourceWallpapersPage(
            wallpapers = response.photos.map { it.toSourceDto() },
            page = response.page,
            hasNextPage = response.nextPage != null,
            totalResults = response.totalResults,
        )
    }

    override suspend fun getByCategory(
        category: WallpaperCategory,
        page: Int,
        perPage: Int,
    ): SourceWallpapersPage {
        val response = api.searchPhotos(category.queryTerm, page, perPage)
        return SourceWallpapersPage(
            wallpapers = response.photos.map { it.toSourceDto(rawCategory = category.name) },
            page = response.page,
            hasNextPage = response.nextPage != null,
            totalResults = response.totalResults,
        )
    }

    override suspend fun getById(id: String): SourceWallpaperDto =
        api.getPhotoById(id).toSourceDto()

    private fun PhotoDto.toSourceDto(rawCategory: String? = null) = SourceWallpaperDto(
        id = id.toString(),
        title = alt.ifBlank { "Wallpaper #$id" },
        description = alt,
        authorName = photographer,
        authorUrl = photographerUrl,
        thumbnailUrl = src.small,
        previewUrl = src.large,
        fullUrl = src.portrait.ifBlank { src.original },
        dominantColor = avgColor,
        width = width,
        height = height,
        category = rawCategory,
        tags = emptyList(),
        sourceType = SourceType.PEXELS,
    )
}
