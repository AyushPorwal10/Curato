package com.curato.wallpapers.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavoriteEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val photographerName: String,
    val photographerUrl: String,
    val thumbnailUrl: String,
    val previewUrl: String,
    val fullUrl: String,
    val dominantColor: String,
    val width: Int,
    val height: Int,
    val category: String?,
    val resolution: String,
    val format: String,
    val curatedBy: String,
    val savedAt: Long = System.currentTimeMillis(),
)
