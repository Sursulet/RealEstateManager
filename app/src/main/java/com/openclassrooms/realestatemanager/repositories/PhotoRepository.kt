package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.data.local.dao.PhotoDao
import com.openclassrooms.realestatemanager.data.local.entities.Photo
import javax.inject.Inject

class PhotoRepository @Inject constructor(
    private val photoDao: PhotoDao
) {

    fun getPhotos(realEstateId: Int) = photoDao.getPhotos(realEstateId)
    fun getAllPhotos() = photoDao.getAllPhotos()
    fun getPhoto(realEstateId: Int) = photoDao.getPhoto(realEstateId)

    //suspend fun insert(photo: Photo) { photoDao.insert(photo) }
    suspend fun insertPhotos(photos: List<Photo>) { photoDao.insertPhotos(photos) }
    suspend fun update(photo: Photo) { photoDao.update(photo) }
    suspend fun delete(photo: Photo) { photoDao.delete(photo) }
}