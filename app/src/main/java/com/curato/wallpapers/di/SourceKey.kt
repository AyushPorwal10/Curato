package com.curato.wallpapers.di

import com.curato.wallpapers.data.source.SourceType
import dagger.MapKey

/**
 * Custom Dagger @MapKey for WallpaperSource multibindings.
 */
@MapKey
annotation class SourceKey(val value: SourceType)
