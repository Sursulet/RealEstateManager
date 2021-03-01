package com.openclassrooms.realestatemanager.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String
)
