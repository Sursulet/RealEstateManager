package com.openclassrooms.realestatemanager.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photo")
    fun getPhotos(): Flow<List<Photo>>

    @Query("SELECT * FROM photo WHERE id = :photoId")
    fun getPhoto(photoId: Int): Flow<Photo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(photo: Photo)

    @Update
    suspend fun update(photo: Photo)

    @Delete
    suspend fun delete(photo: Photo)
}