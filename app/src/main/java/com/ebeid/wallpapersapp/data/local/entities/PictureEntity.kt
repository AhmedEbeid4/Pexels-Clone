package com.ebeid.wallpapersapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_pictures")
data class PictureEntity(
    @PrimaryKey
    val pictureId: Int,
    val pictureWidth: Int,
    val pictureHeight: Int,
    val picturePhotographerName: String,
    val originalPicture: String,
    val large2xPicture: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
)