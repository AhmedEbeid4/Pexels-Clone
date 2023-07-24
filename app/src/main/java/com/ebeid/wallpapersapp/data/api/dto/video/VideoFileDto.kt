package com.ebeid.wallpapersapp.data.api.dto.video

data class VideoFileDto(
    val id: Int,
    val quality: String,
    val file_type: String,
    val width: Int,
    val height: Int,
    val fps: Double,
    val link: String
)