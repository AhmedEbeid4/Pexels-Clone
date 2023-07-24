package com.ebeid.wallpapersapp.domain.models

class Picture(
    pictureId: Int,
    pictureWidth: Int,
    pictureHeight: Int,
    picturePhotographerName: String,
    originalPicture: String,
    val large2xPicture: String,
    val large: String,
    val medium: String,
    val small: String,
    val portrait: String,
    val landscape: String,
    val tiny: String
) : PexelsModel(
    pictureId,
    pictureWidth,
    pictureHeight,
    originalPicture,
    portrait,
    picturePhotographerName,
    0
) {

}