package com.curato.wallpapers.data.source.impl

import com.curato.wallpapers.data.remote.api.PexelsApi
import com.curato.wallpapers.data.remote.dto.PhotoDto
import com.curato.wallpapers.data.remote.dto.PexelsPhotosResponse
import com.curato.wallpapers.data.source.SourceType
import com.curato.wallpapers.data.source.WallpaperSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PexelsWallpaperSource @Inject constructor(
    private val api: PexelsApi,
) : WallpaperSource {

    override val sourceType = SourceType.PEXELS

    override suspend fun getCurated(page: Int, perPage: Int): PexelsPhotosResponse =
        api.getCuratedPhotos(page, perPage)

    override suspend fun search(query: String, page: Int, perPage: Int): PexelsPhotosResponse =
        api.searchPhotos(query, page, perPage)

    override suspend fun getById(id: String): PhotoDto =
        api.getPhotoById(id)
}
