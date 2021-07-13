package com.openclassrooms.realestatemanager.data.local.dao

import android.database.Cursor
import androidx.room.*
import com.openclassrooms.realestatemanager.data.local.entities.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo WHERE realEstateId = :realEstateId")
    fun getPhotos(realEstateId: Long): Flow<List<Photo>>

    @Query("SELECT * FROM photo")
    fun getAllPhotos(): Flow<List<Photo>>

    @Query("SELECT * FROM photo WHERE realEstateId = :realEstateId LIMIT 1")
    fun getPhoto(realEstateId: Long): Flow<Photo>

    @Query("SELECT COUNT(*) FROM photo GROUP BY realEstateId HAVING COUNT(*) <= :size")
    fun search(size: Int): Flow<List<Int>>

    @Query("SELECT * FROM photo WHERE realEstateId = :realEstateId")
    fun getPhotosWithCursor(realEstateId: Long): Cursor

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhoto(photo: Photo)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<Photo>)

    @Update
    suspend fun update(photo: Photo)

    @Delete
    suspend fun delete(photo: Photo)

    //@Query("SELECT * FROM real_estate WHERE photos LIKE '%' || || '%'") //create module gallery
    //fun search()
}