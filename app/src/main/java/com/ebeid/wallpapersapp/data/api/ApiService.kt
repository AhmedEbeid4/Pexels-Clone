package com.ebeid.wallpapersapp.data.api

import com.ebeid.wallpapersapp.data.api.dto.picture.PictureResponseDto
import com.ebeid.wallpapersapp.data.api.dto.video.PopularVideosResponseDto
import com.ebeid.wallpapersapp.data.api.dto.video.VideoDto
import com.ebeid.wallpapersapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PexelsApi {

    @Headers("Authorization: ${Constants.CLIENT_ID}")
    @GET("v1/curated")
    suspend fun getPopularImages(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
    ): Response<PictureResponseDto>


    @Headers("Authorization: ${Constants.CLIENT_ID}")
    @GET("videos/popular")
    suspend fun getPopularVideos(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
    ): Response<PopularVideosResponseDto>


    @Headers("Authorization: ${Constants.CLIENT_ID}")
    @GET("v1/search")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") pageSize: Int,
    ): Response<PictureResponseDto>

    @Headers("Authorization: ${Constants.CLIENT_ID}")
    @GET("videos/search")
    suspend fun getVideos(
        @Query("page") page: Int,
        @Query("query") query: String,
        @Query("per_page") pageSize: Int,
    ): Response<PopularVideosResponseDto>
}