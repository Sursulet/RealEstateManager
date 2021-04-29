package com.openclassrooms.realestatemanager.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.utilities.realEstateA
import com.openclassrooms.realestatemanager.utilities.realEstateB
import com.openclassrooms.realestatemanager.utilities.realEstateC
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
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
    fun setup() = runBlocking {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealEstateManagerDatabase::class.java
        ).allowMainThreadQueries().build()

        dao = database.realEstateDao()

        dao.insert(realEstateA)
        dao.insert(realEstateB)
        dao.insert(realEstateC)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun getRealEstates() = runBlockingTest {
        val allRealEstateItem = dao.getRealEstates().first()
        assertThat(allRealEstateItem.size).isEqualTo(3)

        assertThat(allRealEstateItem[0]).isEqualTo(realEstateA)
        assertThat(allRealEstateItem[1]).isEqualTo(realEstateB)
        assertThat(allRealEstateItem[2]).isEqualTo(realEstateC)
    }

    @Test
    fun getRealEstate() = runBlockingTest {
        val realEstateItem = dao.getRealEstate(realEstateA.id).first()
        assertThat(realEstateItem).isEqualTo(realEstateA)
    }

    @Test
    fun updateRealEstate() = runBlockingTest {
        val x = realEstateA.copy()
        val realEstateItem = dao.getRealEstate(realEstateA.id).first()
        assertThat(realEstateItem).isEqualTo(realEstateA)
    }
}