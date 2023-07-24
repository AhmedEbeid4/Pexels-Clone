package com.ebeid.wallpapersapp.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.ebeid.wallpapersapp.data.api.dto.picture.PictureResponseDto
import com.ebeid.wallpapersapp.domain.models.Picture
import com.ebeid.wallpapersapp.domain.models.SavedItemsState
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface PictureRepository {
    fun getPopularPictures(): Flow<PagingData<Picture>>
    fun getSearchPhotos(query: String): LiveData<PagingData<Picture>>

    suspend fun getSavedPhotos(): Flow<SavedItemsState<MutableList<Picture>>>
    suspend fun savePhoto(pictureModel: Picture)
    suspend fun unSavePhoto(pictureModel: Picture)
}