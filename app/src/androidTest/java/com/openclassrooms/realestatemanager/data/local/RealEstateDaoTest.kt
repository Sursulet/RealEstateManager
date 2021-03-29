package com.openclassrooms.realestatemanager.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class RealEstateDaoTest {

    private lateinit var database: RealEstateManagerDatabase
    private lateinit var dao: RealEstateDao

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealEstateManagerDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.realEstateDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertRealEstate() = runBlockingTest {
        val realEstate = RealEstate(
            id = 4, type = "flat", city = "PARIS", price = 7.1f,
            surface = 0, rooms = 0, bedrooms = 0, bathrooms = 0,
            description = "ghjkl", address = "", nearest = "",
            status = false,
            agent = "PEACH"
        )

        dao.insert(realEstate)

        val allRealEstateItem = dao.getRealEstates()

        //assertThat(allRealEstateItem).contains(realEstate)
    }
}