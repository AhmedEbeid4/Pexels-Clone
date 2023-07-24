package com.ebeid.wallpapersapp.data.api.dto.picture

data class ImageDto(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val photographer: String,
    val photographer_url: String,
    val photographer_id: String,
    val avg_color: String,
    val src: SourceDto,
    val liked: Boolean,
    val alt: String
)