package com.curato.wallpapers.data.remote.api

import com.curato.wallpapers.data.remote.dto.PhotoDto
import com.curato.wallpapers.data.remote.dto.PexelsPhotosResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApi {

    @GET("v1/curated")
    suspend fun getCuratedPhotos(
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 15,
    ): PexelsPhotosResponse

    @GET("v1/search")
    suspend fun searchPhotos(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 15,
        @Query("orientation") orientation: String = "portrait",
    ): PexelsPhotosResponse

    @GET("v1/photos/{id}")
    suspend fun getPhotoById(
        @Path("id") id: String,
    ): PhotoDto
}
