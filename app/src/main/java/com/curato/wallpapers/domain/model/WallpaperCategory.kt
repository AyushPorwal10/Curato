package com.curato.wallpapers.domain.model

enum class WallpaperCategory(val displayName: String, val queryTerm: String) {
    AMOLED("AMOLED", "amoled dark"),
    MINIMAL("Minimal", "minimal clean"),
    NATURE("Nature", "nature landscape"),
    ABSTRACT("Abstract", "abstract art"),
    FOUR_K("4K", "4k ultra hd"),
    DARK("Dark", "dark moody"),
    NEON("Neon", "neon cyberpunk"),
    SPACE("Space", "space galaxy"),
    ARCHITECTURE("Architecture", "architecture city"),
    ANIME("Anime", "anime aesthetic"),
}
