package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealEstateRepository @Inject constructor(
    private val realEstateDao: RealEstateDao
) {

    fun getRealEstates() = realEstateDao.getRealEstates()
    fun getRealEstate(realEstateId: Int) = realEstateDao.getRealEstate(realEstateId)

    suspend fun insert(realEstate: RealEstate) { realEstateDao.insert(realEstate) }
    suspend fun update(realEstate: RealEstate) { realEstateDao.update(realEstate) }

    suspend fun delete(realEstate: RealEstate) { realEstateDao.delete(realEstate) }
    suspend fun deleteAll() { realEstateDao.deleteAll() }

    fun search(minPrice:Float,maxPrice:Float) = realEstateDao.search(minPrice, maxPrice)

    /*
    private val realEstates: Flow<List<RealEstate>> = realEstateDao.getRealEstates()

    private val observableRealEstates = MutableLiveData<Flow<List<RealEstate>>>(realEstates)

    //fun observeAllRealEstateItems(): LiveData<List<RealEstate>> { realEstateDao.getRealEstates() }
     */
}