package com.ebeid.wallpapersapp.presentation.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.ebeid.wallpapersapp.domain.use_case.SearchPhotosUseCase
import com.ebeid.wallpapersapp.domain.use_case.SearchVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val searchPhotosUseCase: SearchPhotosUseCase,
    private val searchVideosUseCase: SearchVideoUseCase
) : ViewModel() {

    var lastQuery = "all"
    private val defaultQuery = MutableLiveData(lastQuery)

    val pictures = defaultQuery.switchMap { query ->
        searchPhotosUseCase(query).cachedIn(viewModelScope)
    }

    val videos = defaultQuery.switchMap { query ->
        searchVideosUseCase(query).cachedIn(viewModelScope)
    }


    fun searchQuery(query: String = lastQuery) {
        lastQuery = query
        defaultQuery.value = lastQuery
    }
}