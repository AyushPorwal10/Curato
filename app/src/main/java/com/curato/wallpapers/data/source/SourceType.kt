package com.curato.wallpapers.data.source

/**
 * Identifies which wallpaper source backend to use.
 * MVP2: add UNSPLASH, AI_GENERATED — register in WallpaperSourceFactory, done.
 */
enum class SourceType {
    PEXELS,
    // UNSPLASH,       // MVP2
    // AI_GENERATED,   // MVP3
}
