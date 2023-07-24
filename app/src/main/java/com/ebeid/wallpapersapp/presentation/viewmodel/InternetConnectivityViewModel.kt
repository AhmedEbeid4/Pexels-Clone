package com.ebeid.wallpapersapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebeid.wallpapersapp.presentation.internet_connectivity.ConnectivityObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InternetConnectivityViewModel
@Inject
constructor(
    private val connectivityObserver: ConnectivityObserver
) : ViewModel() {
    private val _isConnected = MutableLiveData(true)
    val isConnected: LiveData<Boolean>
        get() = _isConnected

    private fun startObserving() {
        viewModelScope.launch {
            connectivityObserver.observe().collect {
                when (it) {
                    ConnectivityObserver.InternetStatus.AVAILABLE -> {
                        _isConnected.postValue(true)
                    }

                    else -> {
                        _isConnected.postValue(false)
                    }
                }
            }
        }
    }

    init {
        startObserving()
    }
}