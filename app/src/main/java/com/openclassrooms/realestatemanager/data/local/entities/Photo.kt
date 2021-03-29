package com.openclassrooms.realestatemanager.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = RealEstate::class,
        parentColumns = ["id"],
        childColumns = ["realEstateId"]
    )],
    indices = [Index("realEstateId")]
)
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val url: String,
    val realEstateId: Int = 0
)
