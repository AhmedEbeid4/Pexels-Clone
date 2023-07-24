package com.ebeid.wallpapersapp.domain.models

data class VideoModel(
    val videoId: Int,
    val videoWidth: Int,
    val videoHeight: Int,
    val fileType: String,
    val quality: String,
    val videoUrl: String,
    val videoPictureUrl: String,
    val videoPhotographerName: String
) : PexelsModel(
    videoId,
    videoWidth,
    videoHeight,
    videoPictureUrl,
    videoPictureUrl,
    videoPhotographerName,
    1
)