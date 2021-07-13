package com.openclassrooms.realestatemanager.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "real_estate")
data class RealEstate(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var type: String,
    var city: String,
    var price: Float?,
    var surface: Int,
    var rooms: Int?,
    var bathrooms: Int?,
    var bedrooms: Int?,
    var description: String,
    var address: String,
    //var lat: Double,
    //var lon: Double,
    var nearest: String, //NEAREST
    var status: Boolean = false,
    val created: LocalDate = LocalDate.now(),
    val saleTimestamp: String?,
    var agent: String
) {
    val createdDateFormatted: String
        get() = created.toString()
}