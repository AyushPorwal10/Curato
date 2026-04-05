package com.curato.wallpapers.di

import android.content.Context
import androidx.room.Room
import com.curato.wallpapers.data.local.CuratoDatabase
import com.curato.wallpapers.data.local.dao.FavoriteDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideCuratoDatabase(@ApplicationContext context: Context): CuratoDatabase =
        Room.databaseBuilder(context, CuratoDatabase::class.java, CuratoDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideFavoriteDao(database: CuratoDatabase): FavoriteDao =
        database.favoriteDao()
}
