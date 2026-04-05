package com.curato.wallpapers.di

import com.curato.wallpapers.data.source.SourceType
import com.curato.wallpapers.data.source.WallpaperSource
import com.curato.wallpapers.data.source.impl.PexelsWallpaperSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

/**
 * Strategy pattern — each SourceType is bound to its WallpaperSource implementation.
 *
 * Hilt builds the Map<SourceType, WallpaperSource> automatically.
 * WallpaperSourceFactory receives it via constructor injection.
 *
 * MVP2 example — adding Unsplash:
 *   1. Create UnSplashWallpaperSource implementing WallpaperSource
 *   2. Add SourceType.UNSPLASH to the enum
 *   3. Add one @Binds below — nothing else changes
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    @IntoMap
    @SourceKey(SourceType.PEXELS)
    abstract fun bindPexelsSource(
        source: PexelsWallpaperSource,
    ): WallpaperSource
}
