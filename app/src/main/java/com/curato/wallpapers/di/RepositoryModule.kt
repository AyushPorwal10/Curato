package com.curato.wallpapers.di

import com.curato.wallpapers.data.repository.FavoriteRepository
import com.curato.wallpapers.data.repository.FavoriteRepositoryImpl
import com.curato.wallpapers.data.repository.WallpaperRepository
import com.curato.wallpapers.data.repository.WallpaperRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWallpaperRepository(impl: WallpaperRepositoryImpl): WallpaperRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(impl: FavoriteRepositoryImpl): FavoriteRepository
}
