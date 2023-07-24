package com.ebeid.wallpapersapp.data

import com.ebeid.wallpapersapp.data.api.dto.picture.ImageDto
import com.ebeid.wallpapersapp.data.api.dto.picture.PictureResponseDto
import com.ebeid.wallpapersapp.data.api.dto.video.PopularVideosResponseDto
import com.ebeid.wallpapersapp.data.api.dto.video.VideoDto
import com.ebeid.wallpapersapp.data.local.entities.PictureEntity
import com.ebeid.wallpapersapp.domain.models.Picture
import com.ebeid.wallpapersapp.domain.models.VideoModel

fun PictureResponseDto.toPictures(): List<Picture> {
    return photos.map { it.toPictureModel() }
}

fun ImageDto.toPictureModel(): Picture = Picture(
    id,
    width,
    height,
    photographer,
    src.original,
    src.large2x,
    src.large,
    src.medium,
    src.small,
    src.portrait,
    src.landscape,
    src.tiny,
)

fun PopularVideosResponseDto.toVideoModels(): List<VideoModel> {
    return videos.map { it.toVideoModel() }
}

fun VideoDto.toVideoModel(): VideoModel = VideoModel(
    id,
    width,
    height,
    video_files[0].file_type,
    video_files[0].quality,
    video_files[0].link,
    video_pictures[0].picture,
    user.name
)

fun PictureEntity.toPictureModel(): Picture = Picture(
    pictureId,
    pictureWidth,
    pictureHeight,
    picturePhotographerName,
    originalPicture,
    large2xPicture,
    large,
    medium,
    small,
    portrait,
    landscape,
    tiny
)

fun Picture.toPictureEntity(): PictureEntity = PictureEntity(
    id,
    width,
    height,
    primaryImageUrl,
    viewImageUrl,
    photographerName,
    large, medium, small, portrait, landscape, tiny
)