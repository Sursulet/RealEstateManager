package com.openclassrooms.realestatemanager.data.local.dao

import android.database.Cursor
import androidx.room.*
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import kotlinx.coroutines.flow.Flow

@Dao
interface RealEstateDao {

    @Query("SELECT * FROM real_estate ")
    fun getRealEstates(): Flow<List<RealEstate>>

    @Query("SELECT * FROM real_estate WHERE id = :realEstateId")
    fun getRealEstate(realEstateId: Int): Flow<RealEstate>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(realEstate: RealEstate)

    @Update
    suspend fun update(realEstate: RealEstate)

    @Delete
    suspend fun delete(realEstate: RealEstate)

    @Query("DELETE FROM real_estate")
    suspend fun deleteAll()

    @Query("SELECT * FROM real_estate WHERE type LIKE '%' || :searchQuery || '%' ORDER BY city DESC ")
    fun sortedBy(searchQuery: String): Flow<List<RealEstate>>

    @Query("SELECT * FROM real_estate WHERE id = :realEstateId")
    fun getRealEstateWithCursor(realEstateId:Int): Cursor
}