package com.ebeid.wallpapersapp.domain.use_case

import com.ebeid.wallpapersapp.domain.repository.PictureRepository
import javax.inject.Inject

class FetchPopularPicturePageUseCase
@Inject
constructor(private val pictureRepository: PictureRepository) {
    operator fun invoke() = pictureRepository.getPopularPictures()

}