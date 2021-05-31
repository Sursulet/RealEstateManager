package com.openclassrooms.realestatemanager.ui.addedit.addeditPhoto

import android.graphics.Bitmap
import com.openclassrooms.realestatemanager.data.local.entities.Photo
import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel

interface OnAddEditPhotoListener {
    fun onSavePhotoClick(photoUiModel: PhotoUiModel)
    //fun onDialogNegativeClick(dialog: AddEditPhotoDialog)
}