package com.ebeid.wallpapersapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ebeid.wallpapersapp.data.local.entities.PictureEntity

@Dao
interface PexelsDao {
    @Query("SELECT * FROM saved_pictures")
    suspend fun getSavedPictures(): MutableList<PictureEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun savePicture(picture: PictureEntity)

    @Query("DELETE FROM saved_pictures WHERE pictureId = :id")
    suspend fun deletePicture(id: Int)

}