package com.openclassrooms.realestatemanager.repositories

import android.database.Cursor
import androidx.room.Query
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealEstateRepository @Inject constructor(
    private val realEstateDao: RealEstateDao
) {

    fun getRealEstates() = realEstateDao.getRealEstates()
    fun getRealEstate(realEstateId: Int) = realEstateDao.getRealEstate(realEstateId)

    suspend fun insert(realEstate: RealEstate) {
        realEstateDao.insert(realEstate)
    }

    suspend fun update(realEstate: RealEstate) {
        realEstateDao.update(realEstate)
    }

    suspend fun delete(realEstate: RealEstate) {
        realEstateDao.delete(realEstate)
    }

    suspend fun deleteAll() {
        realEstateDao.deleteAll()
    }

    fun search(
        type: String,
        zone: String,
        minPrice: Float,
        maxPrice: Float,
        release: LocalDate,
        status: Boolean,
        minSurface: Int,
        maxSurface: Int,
        nearest: String,
        size: Int
    ) =
        realEstateDao.search(
            type = type,
            zone = zone,
            minPrice = minPrice,
            maxPrice = maxPrice,
            release = release,
            status = status,
            minSurface = minSurface,
            maxSurface = maxSurface,
            nearest = nearest,
            size = size
        )

    fun searchPhoto() = realEstateDao.searchPhoto()
    fun searchDate(date: LocalDate?) = realEstateDao.searchDate(date)

    /*
    @Query("SELECT * FROM real_estate WHERE id = :realEstateId")
    fun getRealEstatesWithCursor(realEstateId: Int): Cursor?

     */
}