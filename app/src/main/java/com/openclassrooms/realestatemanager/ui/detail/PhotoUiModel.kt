package com.openclassrooms.realestatemanager.ui.detail

import android.graphics.Bitmap

data class PhotoUiModel(
    val id: Long = 0,
    val bitmap: Bitmap,
    val title: String
)
