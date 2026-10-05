package com.curato.wallpapers.di

import com.curato.wallpapers.data.wallpaper.WallpaperDownloaderImpl
import com.curato.wallpapers.domain.wallpaper.WallpaperDownloader
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WallpaperDownloaderModule {

    @Binds
    @Singleton
    abstract fun bindWallpaperDownloader(impl: WallpaperDownloaderImpl): WallpaperDownloader
}
