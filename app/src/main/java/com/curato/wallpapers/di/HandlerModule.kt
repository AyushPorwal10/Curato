package com.curato.wallpapers.di

import com.curato.wallpapers.data.handler.WallpaperHandler
import com.curato.wallpapers.data.handler.WallpaperRequest
import com.curato.wallpapers.data.handler.impl.CategoryWallpaperHandler
import com.curato.wallpapers.data.handler.impl.CuratedWallpaperHandler
import com.curato.wallpapers.data.handler.impl.DetailWallpaperHandler
import com.curato.wallpapers.data.handler.impl.SearchWallpaperHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

/**
 * Handler Factory pattern — each operation type is bound to its handler.
 *
 * Hilt builds the Map<WallpaperRequest.Type, WallpaperHandler<*,*>> automatically.
 * WallpaperHandlerFactory receives it via constructor injection.
 *
 * MVP2 example — adding a Trending handler:
 *   1. Create TrendingWallpaperHandler
 *   2. Add Type.TRENDING to WallpaperRequest.Type enum
 *   3. Add one @Binds below — nothing else changes
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class HandlerModule {

    @Binds
    @IntoMap
    @HandlerKey(WallpaperRequest.Type.CURATED)
    abstract fun bindCuratedHandler(
        handler: CuratedWallpaperHandler,
    ): WallpaperHandler<*, *>

    @Binds
    @IntoMap
    @HandlerKey(WallpaperRequest.Type.SEARCH)
    abstract fun bindSearchHandler(
        handler: SearchWallpaperHandler,
    ): WallpaperHandler<*, *>

    @Binds
    @IntoMap
    @HandlerKey(WallpaperRequest.Type.CATEGORY)
    abstract fun bindCategoryHandler(
        handler: CategoryWallpaperHandler,
    ): WallpaperHandler<*, *>

    @Binds
    @IntoMap
    @HandlerKey(WallpaperRequest.Type.DETAIL)
    abstract fun bindDetailHandler(
        handler: DetailWallpaperHandler,
    ): WallpaperHandler<*, *>
}
