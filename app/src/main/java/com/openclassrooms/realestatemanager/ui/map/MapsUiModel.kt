package com.openclassrooms.realestatemanager.ui.map

import com.google.android.gms.maps.model.LatLng

data class MapsUiModel(
    val lastLocation: LatLng,
    val markers: List<LatLng>
)
