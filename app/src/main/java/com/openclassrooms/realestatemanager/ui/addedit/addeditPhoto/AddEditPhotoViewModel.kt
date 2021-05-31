package com.openclassrooms.realestatemanager.ui.addedit.addeditPhoto

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.data.local.entities.Photo
import com.openclassrooms.realestatemanager.repositories.AddEditPhotoRepository
import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel
import com.openclassrooms.realestatemanager.utils.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class AddEditPhotoViewModel @Inject constructor(
    private val addEditPhotoRepository: AddEditPhotoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<AddEditPhotoUiState>(AddEditPhotoUiState.Add)
    val uiState = _uiState.asStateFlow()
    val emptyBitmap:Bitmap = Bitmap.createBitmap(300, 300, Bitmap.Config.ARGB_8888)

    var title: String = ""
    var photo: Bitmap? = null

    fun onSaveClick() {
        if (title.isBlank()) {
            //showInvalidInputMessage("onSaveClick: Type cannot be empty")
            showInvalidInputMessage(ErrorMessage.TITLE)
            return
        }

        Log.d(TAG, "onSaveClick: $photo")
        if (photo == null) {
            //showInvalidInputMessage("onSaveClick: Type cannot be empty")
            showInvalidInputMessage(ErrorMessage.PHOTO)
            return
        }

        val newPhoto = PhotoUiModel(title = title, bitmap = photo!!)
        addPhoto(newPhoto)
    }

    fun addPhoto(photoUiModel: PhotoUiModel) {
        _uiState.value = AddEditPhotoUiState.NavigateBackResult(photoUiModel)
        addEditPhotoRepository.setValue(photoUiModel)
    }

    private fun showInvalidInputMessage(message: ErrorMessage) {
        _uiState.value = AddEditPhotoUiState.ShowInvalidInputMessage(message)
    }

    sealed class AddEditPhotoUiState {
        object Add : AddEditPhotoUiState()
        object Edit : AddEditPhotoUiState()
        data class ShowInvalidInputMessage(val msg: ErrorMessage) : AddEditPhotoUiState() //Error
        data class NavigateBackResult(val result: PhotoUiModel) : AddEditPhotoUiState() //Success
    }

    enum class ErrorMessage { TITLE, PHOTO }
}