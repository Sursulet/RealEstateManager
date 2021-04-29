package com.openclassrooms.realestatemanager.ui.detail

import com.google.android.gms.maps.model.LatLng

data class DetailUiModel(
    val id: Int = 0,
    val type: String,
    val city: String,
    val price: Float,
    val surface: String?,
    val rooms: String?,
    val bathrooms: String?,
    val bedrooms: String?,
    val description: String?,
    val address: String?,
    val nearest: String?,
    val status: Boolean = false,
    val photos: List<PhotoUiModel>,
    val coordinates: LatLng?
)
