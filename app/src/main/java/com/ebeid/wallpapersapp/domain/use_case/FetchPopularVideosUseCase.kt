package com.ebeid.wallpapersapp.domain.use_case

import com.ebeid.wallpapersapp.domain.repository.VideoRepository
import javax.inject.Inject

class FetchPopularVideosUseCase
@Inject
constructor(private val repository: VideoRepository){
    operator fun invoke() = repository.getPopularVideos()
}