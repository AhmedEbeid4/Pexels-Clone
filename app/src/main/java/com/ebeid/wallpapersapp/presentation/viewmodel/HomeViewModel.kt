package com.ebeid.wallpapersapp.presentation.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ebeid.wallpapersapp.domain.models.Picture
import com.ebeid.wallpapersapp.domain.models.VideoModel
import com.ebeid.wallpapersapp.domain.use_case.FetchPopularPicturePageUseCase
import com.ebeid.wallpapersapp.domain.use_case.FetchPopularVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    fetchPopularPicturePageUseCase: FetchPopularPicturePageUseCase,
    fetchPopularVideosPageUseCase: FetchPopularVideosUseCase,
) : ViewModel() {

    val picturesList: Flow<PagingData<Picture>> =
        fetchPopularPicturePageUseCase().cachedIn(viewModelScope)
    val videosList: Flow<PagingData<VideoModel>> =
        fetchPopularVideosPageUseCase().cachedIn(viewModelScope)

}