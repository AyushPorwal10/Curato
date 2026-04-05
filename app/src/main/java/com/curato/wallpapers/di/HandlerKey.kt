package com.curato.wallpapers.di

import com.curato.wallpapers.data.handler.WallpaperRequest
import dagger.MapKey

/**
 * Custom Dagger @MapKey for WallpaperHandler multibindings.
 * Using our own annotation (not Dagger's @EnumKey) so KSP can always resolve it.
 */
@MapKey
annotation class HandlerKey(val value: WallpaperRequest.Type)
