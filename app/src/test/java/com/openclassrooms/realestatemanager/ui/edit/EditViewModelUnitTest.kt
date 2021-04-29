package com.openclassrooms.realestatemanager.ui.edit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import com.openclassrooms.realestatemanager.utilities.*
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before

import org.junit.Rule
import org.junit.Test

class EditViewModelUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: EditViewModel

    private val sharedRepository = mockkClass(SharedRepository::class)
    private val realEstateRepository = mockkClass(RealEstateRepository::class)
    private val photoRepository = mockkClass(PhotoRepository::class)

    @Before
    fun setUp() {
        every { sharedRepository.realEstateIdState.value } returns 1
        every { realEstateRepository.getRealEstate(1) } returns flowOf(realEstateA)
        every { photoRepository.getPhotos(1) } returns flowOf(testPhotos)

        viewModel = EditViewModel(
            sharedRepository = sharedRepository,
            realEstateRepository = realEstateRepository,
            photoRepository = photoRepository
        )
    }

    @Test
    fun test() {

        val value = getValue(viewModel.uiLiveData)

        Truth.assertThat(value.type).isEqualTo(realEstateA.type)
        Truth.assertThat(value.city).isEqualTo(realEstateA.city)
        Truth.assertThat(value.price).isEqualTo(realEstateA.price.toString())
        Truth.assertThat(value.surface).isEqualTo("${realEstateA.surface}")
        Truth.assertThat(value.rooms).isEqualTo(realEstateA.rooms.toString())
        Truth.assertThat(value.bedrooms).isEqualTo(realEstateA.bedrooms.toString())
        Truth.assertThat(value.bathrooms).isEqualTo(realEstateA.bathrooms.toString())
        Truth.assertThat(value.description).isEqualTo(realEstateA.description)
        //Truth.assertThat(value.address).isEqualTo(realEstateA.address) address formatted
        Truth.assertThat(value.nearest).isEqualTo(realEstateA.nearest)
        Truth.assertThat(value.status).isEqualTo(realEstateA.status)
        Truth.assertThat(value.photos).isEqualTo(testUiModelPhotos)
        //Truth.assertThat(value.coordinates).isEqualTo(realEstateA.agent)
    }
}