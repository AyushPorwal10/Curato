package com.curato.wallpapers.di

import com.curato.wallpapers.data.source.SourceType
import com.curato.wallpapers.data.source.WallpaperSource
import com.curato.wallpapers.data.source.impl.FirebaseWallpaperSource
import com.curato.wallpapers.data.source.impl.PexelsWallpaperSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

/**
 * To switch back to Pexels: change [WallpaperSourceFactory.getDefault] to return [SourceType.PEXELS].
 * To add a new source: implement [WallpaperSource], add a [SourceType] entry, add one @Binds below.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class SourceModule {

    @Binds
    @IntoMap
    @SourceKey(SourceType.PEXELS)
    abstract fun bindPexelsSource(source: PexelsWallpaperSource): WallpaperSource

    @Binds
    @IntoMap
    @SourceKey(SourceType.FIREBASE)
    abstract fun bindFirebaseSource(source: FirebaseWallpaperSource): WallpaperSource
}
