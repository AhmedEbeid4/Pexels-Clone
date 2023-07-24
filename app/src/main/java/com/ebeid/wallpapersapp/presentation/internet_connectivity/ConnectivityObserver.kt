package com.ebeid.wallpapersapp.presentation.internet_connectivity

import kotlinx.coroutines.flow.Flow


interface ConnectivityObserver {
    fun  observe() : Flow<InternetStatus>

    enum class InternetStatus{
        AVAILABLE, UNAVAILABLE, LOSING, LOST
    }
}