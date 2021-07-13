package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealEstateRepository @Inject constructor(
    private val realEstateDao: RealEstateDao
) {

    fun getRealEstates() = realEstateDao.getRealEstates()

    fun getRealEstate(realEstateId: Long) = realEstateDao.getRealEstate(realEstateId)

    suspend fun insert(realEstate: RealEstate): Long {
        return realEstateDao.insert(realEstate)
    }

    suspend fun update(realEstate: RealEstate) {
        realEstateDao.update(realEstate)
    }

    fun search(
        type: String,
        zone: String,
        minPrice: Float,
        maxPrice: Float,
        release: LocalDate?,
        status: Boolean,
        minSurface: Int,
        maxSurface: Int,
        nearest: String,
        size: Int
    ) = realEstateDao.search(
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

}