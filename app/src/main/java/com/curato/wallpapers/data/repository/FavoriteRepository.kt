package com.curato.wallpapers.data.repository

import com.curato.wallpapers.domain.model.Wallpaper
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    fun observeFavorites(): Flow<List<Wallpaper>>
    fun observeIsFavorite(id: String): Flow<Boolean>
    suspend fun toggleFavorite(wallpaper: Wallpaper)
    suspend fun addFavorite(wallpaper: Wallpaper)
    suspend fun removeFavorite(id: String)
}
