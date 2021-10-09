package com.openclassrooms.realestatemanager.ui.addedit.addeditPhoto

import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel

interface OnAddEditPhotoListener {
    fun onSavePhotoClick(photoUiModel: PhotoUiModel)
    //fun onDialogNegativeClick(dialog: AddEditPhotoDialog)
}