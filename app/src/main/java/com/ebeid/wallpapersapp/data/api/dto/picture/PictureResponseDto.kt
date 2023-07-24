package com.ebeid.wallpapersapp.data.api.dto.picture

data class PictureResponseDto(
    val page:Int,
    val per_page:Int,
    val photos : List<ImageDto>
)