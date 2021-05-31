package com.openclassrooms.realestatemanager.data.local.entities

import android.graphics.Bitmap
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
    val id: Long = 0,
    val title: String,
    val bitmap: Bitmap,
    val realEstateId: Long = 0
)
