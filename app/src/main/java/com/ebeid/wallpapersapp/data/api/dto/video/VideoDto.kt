package com.ebeid.wallpapersapp.data.api.dto.video

data class VideoDto(
    val id: Int,
    val width: Int,
    val height: Int,
    val url: String,
    val image: String,
    val duration: Int,
    val user: VideoUploaderDto,
    val video_files: List<VideoFileDto>,
    val video_pictures: List<VideoPictureDto>
)