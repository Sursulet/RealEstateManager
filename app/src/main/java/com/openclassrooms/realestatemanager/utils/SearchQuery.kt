package com.openclassrooms.realestatemanager.utils

import java.time.LocalDate

data class SearchQuery(
    val type: String,
    val zone: String,
    val minPrice: Float,
    val maxPrice: Float,
    var release: LocalDate,
    val status: Boolean,
    val minSurface: Int,
    val maxSurface: Int,
    val nearest: String,
    val nbPhotos: Int = 1
)
