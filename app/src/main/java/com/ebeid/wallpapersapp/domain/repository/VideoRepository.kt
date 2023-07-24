package com.ebeid.wallpapersapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.ebeid.wallpapersapp.domain.models.VideoModel
import kotlinx.coroutines.flow.Flow

interface VideoRepository {
    fun getPopularVideos(): Flow<PagingData<VideoModel>>
    fun getSearchVideos(query: String): LiveData<PagingData<VideoModel>>
}