package com.ebeid.wallpapersapp.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.ebeid.wallpapersapp.data.api.PexelsApi
import com.ebeid.wallpapersapp.data.local.PexelsDao
import com.ebeid.wallpapersapp.data.repository.paging.PhotosPagingSource
import com.ebeid.wallpapersapp.data.toPictureEntity
import com.ebeid.wallpapersapp.data.toPictureModel
import com.ebeid.wallpapersapp.domain.models.Picture
import com.ebeid.wallpapersapp.domain.models.SavedItemsState
import com.ebeid.wallpapersapp.domain.repository.PictureRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PictureRepositoryImp
@Inject
constructor(
    private val apiService: PexelsApi,
    private val dao: PexelsDao
) : PictureRepository {
    override fun getPopularPictures(): Flow<PagingData<Picture>> = Pager(
        config = PagingConfig(
            pageSize = 1,
        ),
        pagingSourceFactory = {
            PhotosPagingSource(
                apiService,
                null
            )
        }
    ).flow

    override fun getSearchPhotos(query: String): LiveData<PagingData<Picture>> = Pager(
        config = PagingConfig(
            pageSize = 1,
        ),
        pagingSourceFactory = {
            PhotosPagingSource(
                apiService,
                query
            )
        }
    ).liveData

    override suspend fun getSavedPhotos(): Flow<SavedItemsState<MutableList<Picture>>> = flow {
        emit(SavedItemsState.loading())
        try {
            val items = dao.getSavedPictures().map { it.toPictureModel() }.toMutableList()

            emit(SavedItemsState.success(items, items.isEmpty()))
        } catch (e: Exception) {
            emit(SavedItemsState.error(e.message.toString()))
        }
    }

    override suspend fun savePhoto(pictureModel: Picture) {
        dao.savePicture(pictureModel.toPictureEntity())
    }

    override suspend fun unSavePhoto(pictureModel: Picture) {
        dao.deletePicture(pictureModel.id)
    }


}