package com.openclassrooms.realestatemanager.ui.addedit

import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel

data class AddEditUiModel(
    val photos: List<PhotoUiModel> = emptyList(),
    val type: String = "",
    val price: String = "",
    val street: String = "",
    val extras: String = "",
    val city: String = "",
    val code: String = "",
    val country: String = "",
    val nearest: String = "",
    val description: String = "",
    val surface: String = "",
    val rooms: String = "",
    val bathrooms: String = "",
    val bedrooms: String = "",
    val status: Boolean = false,
    val agent: String = ""
)
