package com.ebeid.wallpapersapp.domain.use_case

import com.ebeid.wallpapersapp.domain.repository.VideoRepository
import javax.inject.Inject

class SearchVideoUseCase
@Inject
constructor(private val videoRepo: VideoRepository) {
    operator fun invoke(query: String) = videoRepo.getSearchVideos(query)
}