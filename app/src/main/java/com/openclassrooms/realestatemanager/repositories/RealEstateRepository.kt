package com.openclassrooms.realestatemanager.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.data.local.RealEstate
import com.openclassrooms.realestatemanager.data.local.RealEstateDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealEstateRepository @Inject constructor(
    private val realEstateDao: RealEstateDao
) {

    private val realEstates: Flow<List<RealEstate>> = realEstateDao.getRealEstates()

    private val observableRealEstates = MutableLiveData<Flow<List<RealEstate>>>(realEstates)

    //fun observeAllRealEstateItems(): LiveData<List<RealEstate>> { realEstateDao.getRealEstates() }
    fun getRealEstates() = realEstateDao.getRealEstates()
    fun getRealEstate(realEstateId: Int) = realEstateDao.getRealEstate(realEstateId)

    suspend fun insert(realEstate: RealEstate) { realEstateDao.insert(realEstate) }
    suspend fun update(realEstate: RealEstate) { realEstateDao.update(realEstate) }
    suspend fun delete(realEstate: RealEstate) { realEstateDao.delete(realEstate) }
}