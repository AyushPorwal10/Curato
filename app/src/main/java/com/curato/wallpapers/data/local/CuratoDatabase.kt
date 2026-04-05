package com.curato.wallpapers.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.curato.wallpapers.data.local.dao.FavoriteDao
import com.curato.wallpapers.data.local.entity.FavoriteEntity

@Database(
    entities = [FavoriteEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class CuratoDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        const val DATABASE_NAME = "curato.db"
    }
}
