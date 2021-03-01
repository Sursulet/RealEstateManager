package com.openclassrooms.realestatemanager.data

import java.text.DateFormat

data class RealEstate(
    val id: Int = 0,
    val type: String,
    val price: String,
    val surface: String,
    val rooms: String,
    val bathrooms: String,
    val bedrooms: String,
    val description: String,
    val address: String,
    val vicinity: String,
    val status: Boolean = false,
    val created: Long = System.currentTimeMillis(),
    val saleTimestamp: String,
    val agent: String
) {
    val createdDateFormatted: String
        get() = DateFormat.getDateInstance().format(created)
}
