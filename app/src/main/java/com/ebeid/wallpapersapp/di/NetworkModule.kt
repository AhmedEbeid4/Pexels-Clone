package com.ebeid.wallpapersapp.di

import android.content.Context
import com.ebeid.wallpapersapp.data.api.PexelsApi
import com.ebeid.wallpapersapp.presentation.internet_connectivity.ConnectivityObserver
import com.ebeid.wallpapersapp.presentation.internet_connectivity.InternetConnectivityObserver
import com.ebeid.wallpapersapp.utils.Constants.BASE_URL
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("baseUrl")
    fun provideBaseUrlString(): String = BASE_URL


    @Provides
    @Singleton
    fun provideApiService(@Named("baseUrl") baseUrl: String): PexelsApi = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .baseUrl(baseUrl)
        .build()
        .create(PexelsApi::class.java)


    @Provides
    @Singleton
    fun provideNetworkStateObserver(
        @ApplicationContext context: Context
    ): ConnectivityObserver = InternetConnectivityObserver(context)

}