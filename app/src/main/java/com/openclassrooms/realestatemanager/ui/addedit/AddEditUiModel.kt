package com.openclassrooms.realestatemanager.ui.addedit

import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel

data class AddEditUiModel(
    val photos: List<PhotoUiModel> = emptyList(),
    val type: String = "",
    val price: String = "",
    val address: String = "",
    val city: String = "",
    val state: String = "",
    val nearest: String = "",
    val description: String = "",
    val surface: String = "",
    val rooms: String = "",
    val bathrooms: String = "",
    val bedrooms: String = "",
    val status: Boolean = false,
    val agent: String = ""
)
