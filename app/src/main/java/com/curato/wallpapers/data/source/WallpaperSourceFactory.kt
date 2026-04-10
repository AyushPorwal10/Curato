package com.curato.wallpapers.data.source

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Factory that returns the correct WallpaperSource strategy for a given SourceType.
 * MVP2: inject a new source, add a when branch — zero changes to handlers or repository.
 */
@Singleton
class WallpaperSourceFactory @Inject constructor(
    private val sources: Map<SourceType, @JvmSuppressWildcards WallpaperSource>,
) {
    fun get(type: SourceType): WallpaperSource =
        sources[type] ?: error("No WallpaperSource registered for $type")

    /** Returns the default active source (configurable via RemoteConfig in future) */
    fun getDefault(): WallpaperSource = get(SourceType.FIREBASE)
}
