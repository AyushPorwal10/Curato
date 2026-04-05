package com.curato.wallpapers.data.repository

import com.curato.wallpapers.data.local.dao.FavoriteDao
import com.curato.wallpapers.data.mapper.FavoriteMapper
import com.curato.wallpapers.domain.model.Wallpaper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FavoriteRepositoryImpl @Inject constructor(
    private val dao: FavoriteDao,
    private val mapper: FavoriteMapper,
) : FavoriteRepository {

    override fun observeFavorites(): Flow<List<Wallpaper>> =
        dao.observeAll().map { entities -> entities.map(mapper::toDomain) }

    override fun observeIsFavorite(id: String): Flow<Boolean> =
        dao.observeIsFavorite(id)

    override suspend fun toggleFavorite(wallpaper: Wallpaper) {
        val existing = dao.getById(wallpaper.id)
        if (existing != null) dao.deleteById(wallpaper.id)
        else dao.insert(mapper.toEntity(wallpaper))
    }

    override suspend fun addFavorite(wallpaper: Wallpaper) =
        dao.insert(mapper.toEntity(wallpaper))

    override suspend fun removeFavorite(id: String) =
        dao.deleteById(id)
}
