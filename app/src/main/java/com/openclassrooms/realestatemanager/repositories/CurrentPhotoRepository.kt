package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentPhotoRepository @Inject constructor() {
    private val _photo = MutableStateFlow<List<PhotoUiModel>>(emptyList())
    val photo = _photo.asStateFlow()

    fun setValue(photoUiModel: PhotoUiModel) {
        _photo.value = listOf(photoUiModel)
    }

    fun clear() {
        _photo.value = emptyList()
    }
}