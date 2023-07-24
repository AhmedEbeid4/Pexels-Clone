package com.ebeid.wallpapersapp.di

import android.content.Context
import androidx.room.Room
import com.ebeid.wallpapersapp.data.local.PexelsDao
import com.ebeid.wallpapersapp.data.local.PexelsDatabase
import com.ebeid.wallpapersapp.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    fun provideDB(@ApplicationContext context: Context): PexelsDatabase {
        return Room.databaseBuilder(
            context,
            PexelsDatabase::class.java,
            DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDAO(database: PexelsDatabase): PexelsDao {
        return database.pexelsDao()
    }

}