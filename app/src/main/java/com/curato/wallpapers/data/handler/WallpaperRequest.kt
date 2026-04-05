package com.curato.wallpapers.data.handler

import com.curato.wallpapers.domain.model.WallpaperCategory

/**
 * Typed request objects — each Handler accepts exactly one Request subtype.
 * Adding a new MVP2 operation = new data class here + new Handler class.
 */
sealed class WallpaperRequest {

    enum class Type {
        CURATED,
        SEARCH,
        CATEGORY,
        DETAIL,
        // TRENDING,   // MVP2
        // AI_GENERATE // MVP3
    }

    abstract val type: Type

    data class GetCurated(
        val page: Int = 1,
        val perPage: Int = 15,
    ) : WallpaperRequest() {
        override val type = Type.CURATED
    }

    data class Search(
        val query: String,
        val page: Int = 1,
        val perPage: Int = 15,
    ) : WallpaperRequest() {
        override val type = Type.SEARCH
    }

    data class GetByCategory(
        val category: WallpaperCategory,
        val page: Int = 1,
        val perPage: Int = 15,
    ) : WallpaperRequest() {
        override val type = Type.CATEGORY
    }

    data class GetDetail(
        val id: String,
    ) : WallpaperRequest() {
        override val type = Type.DETAIL
    }
}
