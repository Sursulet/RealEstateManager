package com.openclassrooms.realestatemanager.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import com.openclassrooms.realestatemanager.utilities.*
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before

import org.junit.Rule
import org.junit.Test

class RealEstatesViewModelUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: RealEstatesViewModel

    private var sharedRepository = mockk<SharedRepository>()
    private var realEstatesRepository = mockk<RealEstateRepository>()
    private var photoRepository = mockk<PhotoRepository>()

    @Before
    fun setUp() {
        every { realEstatesRepository.getRealEstates() } returns flowOf(testRealEstates)
        every { photoRepository.getPhoto(1) } returns flowOf(photoA)
        every { photoRepository.getPhoto(2) } returns flowOf(photoB)
        every { photoRepository.getPhoto(3) } returns flowOf(photoC)

        viewModel = RealEstatesViewModel(
            sharedRepository = sharedRepository,
            realEstateRepository = realEstatesRepository,
            photoRepository = photoRepository
        )
    }

    @Test
    fun `insert gr`() {

        val value = getValue(viewModel.uiModelsLiveData)

        Truth.assertThat(value.size).isEqualTo(3)
        assertUiModel(value, 0)
        assertUiModel(value, 1)
        assertUiModel(value, 2)

        verify(exactly = 1) {
            realEstatesRepository.getRealEstates()
            photoRepository.getPhoto(1)
            photoRepository.getPhoto(2)
            photoRepository.getPhoto(3)
        }
        confirmVerified(realEstatesRepository, photoRepository, sharedRepository)
    }

    @Test
    fun `should not display photo if not found database` () {
        every { photoRepository.getPhoto(1) } returns flowOf()

        val value = getValue(viewModel.uiModelsLiveData)
        Truth.assertThat(value.size).isEqualTo(3)
    }

    private fun assertUiModel(items: List<RealEstateUiModel>, index: Int) {
        Truth.assertThat(items[index].id).isEqualTo(testRealEstates[index].id)
        Truth.assertThat(items[index].url).isEqualTo(testPhotos[0].url)
        Truth.assertThat(items[index].type).isEqualTo(testRealEstates[index].type)
        Truth.assertThat(items[index].city).isEqualTo(testRealEstates[index].city)
        Truth.assertThat(items[index].price).isEqualTo("$${testRealEstates[index].price}")
        //Truth.assertThat(items[index].style).isEqualTo(testRealEstates[index].price)
    }
}