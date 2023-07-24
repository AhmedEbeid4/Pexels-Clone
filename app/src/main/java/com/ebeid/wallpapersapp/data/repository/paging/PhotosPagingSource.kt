package com.ebeid.wallpapersapp.data.repository.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ebeid.wallpapersapp.utils.Constants.DEFAULT_PICTURE_PAGE_SIZE
import com.ebeid.wallpapersapp.data.api.PexelsApi
import com.ebeid.wallpapersapp.data.toPictures
import com.ebeid.wallpapersapp.domain.models.Picture
import retrofit2.HttpException

class PhotosPagingSource(
    val api: PexelsApi,
    private val query: String?
) : PagingSource<Int, Picture>() {


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Picture> {
        return try {
            val currentPage = params.key ?: 1
            val response = if (query != null) {
                api.getPhotos(currentPage, query, DEFAULT_PICTURE_PAGE_SIZE)
            } else {
                api.getPopularImages(currentPage, DEFAULT_PICTURE_PAGE_SIZE)
            }
            val data = response.body()!!.toPictures()
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


    override fun getRefreshKey(state: PagingState<Int, Picture>): Int? = null
}