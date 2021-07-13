package com.openclassrooms.realestatemanager.ui.addedit.addeditPhoto

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.repositories.CurrentPhotoRepository
import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddEditPhotoViewModel @Inject constructor(
    private val currentPhotoRepository: CurrentPhotoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddEditPhotoUiState>(AddEditPhotoUiState.Add)
    val uiState = _uiState.asStateFlow()

    var title: String = ""
    var photo: Bitmap? = null

    fun onSaveClick() {
        if (title.isBlank()) {
            showInvalidInputMessage(ErrorMessage.TITLE)
            return
        }

        if (photo == null) {
            showInvalidInputMessage(ErrorMessage.PHOTO)
            return
        }

        val newPhoto = PhotoUiModel(title = title, bitmap = photo!!)
        addPhoto(newPhoto)
    }

    private fun addPhoto(photoUiModel: PhotoUiModel) {
        _uiState.value = AddEditPhotoUiState.NavigateBackResult(photoUiModel)
        currentPhotoRepository.setValue(photoUiModel)
    }

    private fun showInvalidInputMessage(message: ErrorMessage) {
        _uiState.value = AddEditPhotoUiState.ShowInvalidInputMessage(message)
    }

    sealed class AddEditPhotoUiState {
        object Add : AddEditPhotoUiState()
        data class ShowInvalidInputMessage(val msg: ErrorMessage) : AddEditPhotoUiState() //Error
        data class NavigateBackResult(val result: PhotoUiModel) : AddEditPhotoUiState() //Success
    }

    enum class ErrorMessage { TITLE, PHOTO }
}