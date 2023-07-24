package com.ebeid.wallpapersapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ebeid.wallpapersapp.data.api.PexelsApi
import com.ebeid.wallpapersapp.data.repository.paging.VideosPagingSource
import com.ebeid.wallpapersapp.domain.models.VideoModel
import com.ebeid.wallpapersapp.domain.repository.VideoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VideoRepositoryImp
@Inject
constructor(
    private val apiService: PexelsApi
) : VideoRepository {
    override fun getPopularVideos(): Flow<PagingData<VideoModel>> = Pager(
        config = PagingConfig(
            pageSize = 1,
        ),
        pagingSourceFactory = {
            VideosPagingSource(
                apiService,
                null
            )
        }
    ).flow

    override fun getSearchVideos(query: String): LiveData<PagingData<VideoModel>> = Pager(
        config = PagingConfig(
            pageSize = 1,
        ),
        pagingSourceFactory = {
            VideosPagingSource(
                apiService,
                query
            )
        }
    ).liveData
}