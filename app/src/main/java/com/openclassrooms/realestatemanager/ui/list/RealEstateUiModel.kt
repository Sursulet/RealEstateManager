package com.openclassrooms.realestatemanager.ui.list

import android.graphics.Bitmap

data class RealEstateUiModel(
    val id: Long,
    val bitmap: Bitmap,
    val type: String,
    val city: String,
    val price: String,
    val backgroundColorRes: Int,
    val style: String
)
