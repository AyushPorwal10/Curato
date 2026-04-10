package com.curato.wallpapers.di

import com.curato.wallpapers.data.wallpaper.WallpaperApplierImpl
import com.curato.wallpapers.domain.wallpaper.WallpaperApplier
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class WallpaperApplierModule {

    @Binds
    @Singleton
    abstract fun bindWallpaperApplier(impl: WallpaperApplierImpl): WallpaperApplier
}
