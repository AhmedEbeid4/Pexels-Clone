package com.ebeid.wallpapersapp.domain.use_case

import com.ebeid.wallpapersapp.domain.models.Picture
import com.ebeid.wallpapersapp.domain.repository.PictureRepository
import javax.inject.Inject

class SavePictureUseCase
@Inject
constructor(private val pictureRepository: PictureRepository) {
    suspend operator fun invoke(pictureModel: Picture) = pictureRepository.savePhoto(pictureModel)
}