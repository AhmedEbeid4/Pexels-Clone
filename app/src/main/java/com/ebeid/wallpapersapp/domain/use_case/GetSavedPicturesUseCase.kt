package com.ebeid.wallpapersapp.domain.use_case

import com.ebeid.wallpapersapp.domain.repository.PictureRepository
import javax.inject.Inject

class GetSavedPicturesUseCase
@Inject
constructor(private val pictureRepository: PictureRepository) {
    suspend operator fun invoke() = pictureRepository.getSavedPhotos()
}