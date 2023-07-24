package com.ebeid.wallpapersapp.di

import com.ebeid.wallpapersapp.data.api.PexelsApi
import com.ebeid.wallpapersapp.data.local.PexelsDao
import com.ebeid.wallpapersapp.data.repository.PictureRepositoryImp
import com.ebeid.wallpapersapp.data.repository.VideoRepositoryImp
import com.ebeid.wallpapersapp.domain.repository.PictureRepository
import com.ebeid.wallpapersapp.domain.repository.VideoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun providePictureRepository(apiService: PexelsApi, dao: PexelsDao): PictureRepository {
        return PictureRepositoryImp(apiService,dao)
    }

    @Provides
    @Singleton
    fun provideVideoRepository(apiService: PexelsApi): VideoRepository {
        return VideoRepositoryImp(apiService)
    }
}