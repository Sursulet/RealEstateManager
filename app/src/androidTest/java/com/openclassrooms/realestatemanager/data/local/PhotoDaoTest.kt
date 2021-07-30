package com.openclassrooms.realestatemanager.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth
import com.openclassrooms.realestatemanager.data.local.dao.PhotoDao
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@SmallTest
class PhotoDaoTest {

    private lateinit var database: RealEstateManagerDatabase
    private lateinit var dao: PhotoDao
    private lateinit var realEstateDao: RealEstateDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() = runBlockingTest {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealEstateManagerDatabase::class.java
        ).allowMainThreadQueries().build()

        realEstateDao = database.realEstateDao()
        dao = database.photoDao()

        ////realEstateDao.insert(realEstateA)
        ////dao.insertPhotos(testPhotos)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun getPhotos() = runBlockingTest {
        val allPhotoItem = dao.getAllPhotos().first()
        Truth.assertThat(allPhotoItem.size).isEqualTo(3)

        //Truth.assertThat(allPhotoItem[0]).isEqualTo(testPhotos[0])
        //Truth.assertThat(allPhotoItem[1]).isEqualTo(testPhotos[1])
        //Truth.assertThat(allPhotoItem[2]).isEqualTo(testPhotos[2])
    }
}