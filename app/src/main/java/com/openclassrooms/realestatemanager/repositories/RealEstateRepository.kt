package com.openclassrooms.realestatemanager.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RealEstateRepository @Inject constructor(
    private val realEstateDao: RealEstateDao
) {

    //TODO: Est-ce correct ?
    var realEstateIdPending = MutableStateFlow(0)

    fun getRealEstatePending() = realEstateIdPending.flatMapLatest { realEstateDao.getRealEstate(it) }

    fun getRealEstates() = realEstateDao.getRealEstates()
    fun getRealEstate(realEstateId: Int) = realEstateDao.getRealEstate(realEstateId)

    suspend fun insert(realEstate: RealEstate) { realEstateDao.insert(realEstate) }
    suspend fun update(realEstate: RealEstate) { realEstateDao.update(realEstate) }

    suspend fun delete(realEstate: RealEstate) { realEstateDao.delete(realEstate) }
    suspend fun deleteAll() { realEstateDao.deleteAll() }

    /*
    private val realEstates: Flow<List<RealEstate>> = realEstateDao.getRealEstates()

    private val observableRealEstates = MutableLiveData<Flow<List<RealEstate>>>(realEstates)

    //fun observeAllRealEstateItems(): LiveData<List<RealEstate>> { realEstateDao.getRealEstates() }
     */
}