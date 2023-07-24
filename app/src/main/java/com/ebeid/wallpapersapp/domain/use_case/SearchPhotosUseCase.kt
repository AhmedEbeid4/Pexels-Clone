package com.ebeid.wallpapersapp.domain.use_case

import com.ebeid.wallpapersapp.domain.repository.PictureRepository
import javax.inject.Inject

class SearchPhotosUseCase
@Inject
constructor(private val pictureRepository: PictureRepository) {
    operator fun invoke(query: String) = pictureRepository.getSearchPhotos(query)
}