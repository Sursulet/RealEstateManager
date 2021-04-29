package com.openclassrooms.realestatemanager.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.openclassrooms.realestatemanager.data.local.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.getOrAwaitValue
import com.openclassrooms.realestatemanager.repositories.GeocoderRepository
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import com.openclassrooms.realestatemanager.utilities.realEstateA
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn

class DetailViewModelTest {

    private lateinit var database: RealEstateManagerDatabase
    private lateinit var viewModel: DetailViewModel

    @Mock private lateinit var sharedRepository: SharedRepository
    private lateinit var realEstateRepository: RealEstateRepository
    private lateinit var photoRepository: PhotoRepository
    private lateinit var geocoderRepository: GeocoderRepository

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealEstateManagerDatabase::class.java
        ).allowMainThreadQueries().build()

        doReturn(realEstateA.id).`when`(sharedRepository).realEstateIdState
        //sharedRepository.setRealEstateId(realEstateId = realEstateA.id)

        viewModel = DetailViewModel(
            sharedRepository = sharedRepository,
            realEstateRepository = realEstateRepository,
            photoRepository = photoRepository,
            geocoderRepository = geocoderRepository
        )
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun test() {
        sharedRepository.setRealEstateId(realEstateId = realEstateA.id)
        val x = viewModel.uiModelLiveData.getOrAwaitValue()
        assertThat(x.id).isEqualTo(realEstateA.id)
        assertThat(x.type).isEqualTo(realEstateA.type)
    }
}