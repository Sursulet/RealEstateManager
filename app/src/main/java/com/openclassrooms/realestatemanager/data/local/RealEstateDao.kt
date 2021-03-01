package com.openclassrooms.realestatemanager.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface RealEstateDao {

    @Query("SELECT * FROM real_estate")
    fun getRealEstates(): Flow<List<RealEstate>>
    //fun observeAllRealEstateItem(): LiveData<List<RealEstate>>

    @Query("SELECT * FROM real_estate WHERE id = :realEstateId")
    fun getRealEstate(realEstateId: Int): Flow<RealEstate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(realEstate: RealEstate)

    @Update
    suspend fun update(realEstate: RealEstate)

    @Delete
    suspend fun delete(realEstate: RealEstate)
}