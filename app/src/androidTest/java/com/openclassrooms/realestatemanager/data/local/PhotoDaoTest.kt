package com.openclassrooms.realestatemanager.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.openclassrooms.realestatemanager.data.local.dao.PhotoDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@SmallTest
class PhotoDaoTest {

    private lateinit var database: RealEstateManagerDatabase
    private lateinit var dao: PhotoDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealEstateManagerDatabase::class.java
        ).allowMainThreadQueries().build()

        //dao = database.photoDao()
    }

    @After
    fun teardown() {
        database.close()
    }
}