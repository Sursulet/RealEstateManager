package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.data.local.dao.PhotoDao
import com.openclassrooms.realestatemanager.data.local.entities.Photo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PhotoRepository @Inject constructor(
    private val photoDao: PhotoDao
) {

    fun getPhotos(realEstateId: Long) = photoDao.getPhotos(realEstateId)
    fun getPhoto(realEstateId: Long) = photoDao.getPhoto(realEstateId)
    fun search(size: Int) = photoDao.search(size = size)

    suspend fun insertPhoto(photo: Photo) { photoDao.insertPhoto(photo) }
    //suspend fun insertPhotos(photos: List<Photo>) { photoDao.insertPhotos(photos) }
    suspend fun update(photo: Photo) { photoDao.update(photo) }
    suspend fun delete(photo: Photo) { photoDao.delete(photo) }
}