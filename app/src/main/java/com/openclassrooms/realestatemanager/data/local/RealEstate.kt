package com.openclassrooms.realestatemanager.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat

@Entity(tableName = "real_estate")
data class RealEstate(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String,
    val city: String,
    val price: Float,
    val surface: Int,
    val rooms: Int,
    val bathrooms: Int,
    val bedrooms: Int,
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
