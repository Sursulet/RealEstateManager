package com.openclassrooms.realestatemanager.data

import androidx.room.TypeConverter
import java.time.LocalDate
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: String?): LocalDate? {
        return LocalDate.parse(value)
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDate?): String? {
        return date?.toString()
    }
}