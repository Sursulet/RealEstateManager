package com.openclassrooms.realestatemanager.data.local.entities

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    val nearest: String, //NEAREST
    val status: Boolean = false,/*
    val created: Long = System.currentTimeMillis(),
    val saleTimestamp: String?,*/
    //val url: String,
    //val photosDescription: String,
    val agent: String
) /* {
    val createdDateFormatted: String
        get() = DateFormat.getDateInstance().format(created)

        fun fromContentValues(values: ContentValues) {
            val realEstate: RealEstate
            if(values.containsKey("type")) realEstate.type = values.getAsString("type")
            if(values.containsKey("city")) realEstate.city = values.getAsString("city")
            if(values.containsKey("price")) realEstate.price = values.getAsFloat("price")
            if(values.containsKey("surface")) realEstate.surface = values.getAsInteger("surface")
            if(values.containsKey("rooms")) realEstate.rooms = values.getAsInteger("rooms")
            if(values.containsKey("bathrooms")) realEstate.bathrooms = values.getAsInteger("bathrooms")
            if(values.containsKey("bedrooms")) realEstate.bedrooms = values.getAsInteger("bedrooms")
            if(values.containsKey("description")) realEstate.description = values.getAsString("description")
            if(values.containsKey("address")) realEstate.address = values.getAsString("address")
            if(values.containsKey("nearest")) realEstate.nearest = values.getAsString("nearest")
            if(values.containsKey("status")) realEstate.status = values.getAsBoolean("status")
            if(values.containsKey("agent")) realEstate.agent = values.getAsString("agent")
        }
}
*/