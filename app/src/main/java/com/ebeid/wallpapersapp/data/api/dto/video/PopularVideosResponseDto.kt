package com.ebeid.wallpapersapp.data.api.dto.video

data class PopularVideosResponseDto(
    val page: Int,
    val per_page: Int,
    val total_results: Int,
    val url: String,
    val videos:List<VideoDto>
)