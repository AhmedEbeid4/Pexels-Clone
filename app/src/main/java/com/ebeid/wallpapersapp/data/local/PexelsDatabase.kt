package com.ebeid.wallpapersapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ebeid.wallpapersapp.data.local.entities.PictureEntity

@Database(entities = [PictureEntity::class], version = 2, exportSchema = false)
abstract class PexelsDatabase : RoomDatabase() {
    abstract fun pexelsDao(): PexelsDao
}