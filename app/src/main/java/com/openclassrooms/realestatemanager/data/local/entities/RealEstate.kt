package com.openclassrooms.realestatemanager.data.local.entities

import android.content.ContentValues
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.DateFormat
import java.time.LocalDate

@Entity(tableName = "real_estate")
data class RealEstate(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var type: String,
    var city: String,
    var price: Float?,
    var surface: Int,
    var rooms: Int?,
    var bathrooms: Int?,
    var bedrooms: Int?,
    var description: String,
    var address: String,
    var nearest: String, //NEAREST
    var status: Boolean = false,
    val created: LocalDate = LocalDate.now(),
    //val saleTimestamp: String?,
    var agent: String
) {
    val createdDateFormatted: String
        get() = created.toString()

    //constructor(): this(0,"","",0f,0,0,0,0,0,"","","",false,0, "")

    /*
    fun fromContentValues(values: ContentValues): RealEstate {
        val realEstate = RealEstate()
        if (values.containsKey("type")) realEstate.type = values.getAsString("type")
        if (values.containsKey("city")) realEstate.city = values.getAsString("city")
        if (values.containsKey("price")) realEstate.price = values.getAsFloat("price")
        if (values.containsKey("surface")) realEstate.surface = values.getAsInteger("surface")
        if (values.containsKey("rooms")) realEstate.rooms = values.getAsInteger("rooms")
        if (values.containsKey("bathrooms")) realEstate.bathrooms = values.getAsInteger("bathrooms")
        if (values.containsKey("bedrooms")) realEstate.bedrooms = values.getAsInteger("bedrooms")
        if (values.containsKey("description")) realEstate.description =
            values.getAsString("description")
        if (values.containsKey("address")) realEstate.address = values.getAsString("address")
        if (values.containsKey("nearest")) realEstate.nearest = values.getAsString("nearest")
        if (values.containsKey("status")) realEstate.status = values.getAsBoolean("status")
        if (values.containsKey("agent")) realEstate.agent = values.getAsString("agent")
    }

     */
}