package com.openclassrooms.realestatemanager.ui.edit

import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel

data class EditUiModel(
    val photos: List<PhotoUiModel>,
    val type: String,
    val price: String?,
    val address: String,
    val city: String,
    val state: String,
    val nearest: String,
    val description: String,
    val surface: String,
    val rooms: String?,
    val bathrooms: String?,
    val bedrooms: String?,
    val status: Boolean,
    val agent: String
)
