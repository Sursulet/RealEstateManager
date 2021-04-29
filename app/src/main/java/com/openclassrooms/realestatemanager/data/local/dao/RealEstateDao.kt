package com.openclassrooms.realestatemanager.data.local.dao

import androidx.room.*
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

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


    /* ** *SEARCH* ** */
    //@Query("SELECT * FROM real_estate WHERE city LIKE '%' || :location || '%' AND price LIKE '%' || :minPrice || '%' AND nearest LIKE '%' || :nearest || '%'")
    //@Query("SELECT * FROM real_estate WHERE type LIKE '%' || :type || '%' AND city LIKE '%' || :zone || '%' AND price > :minPrice AND price < :maxPrice ")
    @Query("SELECT * FROM real_estate WHERE type LIKE '%' || :type || '%' AND city LIKE '%' || :zone || '%' AND price > :minPrice AND price < :maxPrice AND created <= :release AND status LIKE '%' || :status || '%' AND surface > :minSurface AND surface < :maxSurface AND nearest LIKE '%' || :nearest || '%' AND EXISTS (SELECT COUNT(*) FROM photo WHERE photo.realEstateId = real_estate.id GROUP BY realEstateId HAVING COUNT(*) >= :size)")
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
    ): Flow<List<RealEstate>>

    @Query("SELECT * FROM real_estate WHERE city LIKE :query ORDER BY price")
    fun searchQuery(query: String): Flow<List<RealEstate>>

    @Query("SELECT * FROM real_estate WHERE created >= :query")
    fun searchDate(query: LocalDate?): Flow<List<RealEstate>>

    @Query("SELECT * FROM real_estate WHERE EXISTS (SELECT COUNT(*) FROM photo WHERE photo.realEstateId = real_estate.id GROUP BY realEstateId HAVING COUNT(*) >= 4)")
    fun searchPhoto(): Flow<List<RealEstate>>

    @Query("SELECT * FROM real_estate WHERE type LIKE '%' || :searchQuery || '%' ORDER BY city DESC ")
    fun sortedBy(searchQuery: String): Flow<List<RealEstate>>

    //@Query("SELECT * FROM real_estate WHERE id = :realEstateId")
    //fun getRealEstateWithCursor(realEstateId:Int): Cursor
}