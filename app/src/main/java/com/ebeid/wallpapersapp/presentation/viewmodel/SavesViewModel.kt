package com.ebeid.wallpapersapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ebeid.wallpapersapp.domain.models.PexelsModel
import com.ebeid.wallpapersapp.domain.models.Picture
import com.ebeid.wallpapersapp.domain.models.SavedItemsState.Status.SUCCESS
import com.ebeid.wallpapersapp.domain.models.SavedItemsState
import com.ebeid.wallpapersapp.domain.use_case.GetSavedPicturesUseCase
import com.ebeid.wallpapersapp.domain.use_case.SavePictureUseCase
import com.ebeid.wallpapersapp.domain.use_case.UnSavePictureUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavesViewModel
@Inject
constructor(
    private val getSavedPicturesUseCase: GetSavedPicturesUseCase,
    private val savePictureUseCase: SavePictureUseCase,
    private val unSavePictureUseCase: UnSavePictureUseCase
) : ViewModel() {
    private val _savedPicturesList = MutableLiveData<SavedItemsState<MutableList<Picture>>>()
    val savedPicturesList: LiveData<SavedItemsState<MutableList<Picture>>>
        get() = _savedPicturesList

    private val _savedPicturesMap = MutableLiveData<MutableMap<Int, Picture>>()
    val savedPicturesMap: LiveData<MutableMap<Int, Picture>>
        get() = _savedPicturesMap

    fun getSavedPictures() {
        viewModelScope.launch {
            getSavedPicturesUseCase().collect {

                if (it.status == SUCCESS) {
                    val map = mutableMapOf<Int, Picture>()
                    it.data!!.map { item -> map[item.id] = item }
                    _savedPicturesMap.postValue(map)
                }
                _savedPicturesList.postValue(it as SavedItemsState<MutableList<Picture>>)
            }
        }
    }

    fun hasSaved(pexelsModel: PexelsModel): Boolean =
        _savedPicturesMap.value!!.contains(pexelsModel.id)


    fun savePicture(picture: Picture) {
        _savedPicturesMap.value!![picture.id] = picture
        viewModelScope.launch {
            savePictureUseCase(picture)
            addItem(picture)
            Log.i("VIEW_MODEL_SCOPE", _savedPicturesList.value!!.data.toString())
            Log.i("VIEW_MODEL_SCOPE", _savedPicturesList.value!!.data!!.size.toString())
        }
    }


    fun unSavePicture(picture: Picture) {
        _savedPicturesMap.value!!.remove(picture.id)
        viewModelScope.launch {
            unSavePictureUseCase(picture)
            removeItem(picture)
            Log.i("VIEW_MODEL_SCOPE", _savedPicturesList.value!!.data.toString())
            Log.i("VIEW_MODEL_SCOPE", _savedPicturesList.value!!.data!!.size.toString())
        }
    }


    fun unSaveSavedPicture(picture: Picture) {
        _savedPicturesMap.value!!.remove(picture.id)
        viewModelScope.launch {
            unSavePictureUseCase(picture)
            _savedPicturesList.value?.let { savedItemsState ->
                val modifiedList = savedItemsState.data!!.toMutableList()
                modifiedList.removeIf { it.id == picture.id }
                _savedPicturesList.postValue(
                    SavedItemsState.success(
                        modifiedList,
                        modifiedList.isEmpty()
                    )
                )

            }
            Log . i ("VIEW_MODEL_SCOPE", _savedPicturesList.value!!.data.toString())
            Log.i("VIEW_MODEL_SCOPE", _savedPicturesList.value!!.data!!.size.toString())
        }
    }

    private fun addItem(picture: Picture) = _savedPicturesList.value!!.data!!.add(picture)


    private fun removeItem(picture: Picture) {
        _savedPicturesList.value!!.data!!.removeIf { it.id == picture.id }
    }


    private fun removeItem(index: Int) = _savedPicturesList.value!!.data!!.removeAt(index)


}