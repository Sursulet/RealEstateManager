package com.openclassrooms.realestatemanager.ui.map

import com.google.android.gms.maps.model.LatLng

data class MarkerUiModel(
    val id: Long,
    val coordinates: LatLng
)
