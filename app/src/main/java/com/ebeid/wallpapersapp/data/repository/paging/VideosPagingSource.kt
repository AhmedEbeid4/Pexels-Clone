package com.ebeid.wallpapersapp.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ebeid.wallpapersapp.data.api.PexelsApi
import com.ebeid.wallpapersapp.data.toVideoModels
import com.ebeid.wallpapersapp.utils.Constants.DEFAULT_VIDEO_PAGE_SIZE
import com.ebeid.wallpapersapp.domain.models.VideoModel
import retrofit2.HttpException

class VideosPagingSource(
    val api: PexelsApi,
    private val query: String?
) : PagingSource<Int, VideoModel>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoModel> {
        return try {
            val currentPage = params.key ?: 1
            val response = if (query != null) {
                api.getVideos(currentPage, query, DEFAULT_VIDEO_PAGE_SIZE)
            } else {
                api.getPopularVideos(currentPage, DEFAULT_VIDEO_PAGE_SIZE)
            }
            val data = response.body()!!.toVideoModels()
            LoadResult.Page(
                data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, VideoModel>): Int? = null

}