package com.openclassrooms.realestatemanager.data.local.dao

import androidx.room.*
import com.openclassrooms.realestatemanager.data.local.entities.Photo
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo WHERE realEstateId = :realEstateId")
    fun getPhotos(realEstateId: Int): Flow<List<Photo>>

    @Query("SELECT * FROM photo")
    fun getAllPhotos(): Flow<List<Photo>>

    @Query("SELECT * FROM photo WHERE id = :photoId")
    fun getPhoto(photoId: Int): Flow<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPhotos(photos: List<Photo>)

    @Update
    suspend fun update(photo: Photo)

    @Delete
    suspend fun delete(photo: Photo)
}