package com.openclassrooms.realestatemanager.ui.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth
import com.openclassrooms.realestatemanager.di.CoroutinesDispatchers
import com.openclassrooms.realestatemanager.repositories.*
import com.openclassrooms.realestatemanager.utilities.*
import com.openclassrooms.realestatemanager.utils.SearchQuery
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class RealEstatesViewModelUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    @get:Rule
    var testCoroutineRule = TestCoroutineRule()

    private lateinit var viewModel: RealEstatesViewModel

    private var twoPaneRepository = mockk<TwoPaneRepository>()
    private var currentIdRepository = mockk<CurrentIdRepository>()
    private var realEstatesRepository = mockk<RealEstateRepository>()
    private var photoRepository = mockk<PhotoRepository>()
    private var searchQueryRepository = mockk<SearchQueryRepository>()

    val searchQuery = SearchQuery(
        type = "House",
        zone = "",
        minPrice = 0f,
        maxPrice = Float.MAX_VALUE,
        release = LocalDate.MAX,
        status = false,
        minSurface = 0,
        maxSurface = Int.MAX_VALUE,
        nearest = "",
        nbPhotos = 1
    )

    private val searchRealEstates = MutableStateFlow(listOf(realEstateB, realEstateC))

    @Before
    fun setUp() {
        every { twoPaneRepository.twoPane } returns flowOf(false)
        every { searchQueryRepository.searchQuery } returns flowOf(searchQuery)
        every { realEstatesRepository.getRealEstates() } returns flowOf(testRealEstates)
        every { photoRepository.getPhoto(1) } returns flowOf(photoA)
        every { photoRepository.getPhoto(2) } returns flowOf(photoB)
        every { photoRepository.getPhoto(3) } returns flowOf(photoC)
        every { realEstatesRepository.search(type = any(),
            zone = any(),
            minPrice = any(),
            maxPrice = any(),
            release = any(),
            status = any(),
            minSurface = any(),
            maxSurface = any(),
            nearest = any(),
            size = any()) } returns searchRealEstates

        viewModel = RealEstatesViewModel(
            twoPaneRepository = twoPaneRepository,
            currentIdRepository = currentIdRepository,
            realEstateRepository = realEstatesRepository,
            photoRepository = photoRepository,
            searchQueryRepository = searchQueryRepository,
            coroutineDispatchers = CoroutinesDispatchers(
                testCoroutineRule.testCoroutineDispatcher,
                testCoroutineRule.testCoroutineDispatcher
            )
        )
    }

    @Test
    fun `insert items`() = testCoroutineRule.runBlockingTest {

        val value = viewModel.uiModels.first()

        Truth.assertThat(value.size).isEqualTo(2)
        /*
        assertUiModel(value, 0)
        assertUiModel(value, 1)
        assertUiModel(value, 2)

        verify(exactly = 1) {
            realEstatesRepository.getRealEstates()
            photoRepository.getPhoto(1)
            photoRepository.getPhoto(2)
            photoRepository.getPhoto(3)
        }
        confirmVerified(realEstatesRepository, photoRepository, currentIdRepository)

         */
    }

    @Test
    fun `search House`() = testCoroutineRule.runBlockingTest {

        every { realEstatesRepository.search(type = "House",
            zone = "",
            minPrice = 0f,
            maxPrice = Float.MAX_VALUE,
            release = LocalDate.MAX,
            status = false,
            minSurface = 0,
            maxSurface = Int.MAX_VALUE,
            nearest = "",
            size = 1) } returns flowOf(listOf(realEstateB, realEstateC))



        val value = viewModel.uiModels.first()

        Truth.assertThat(value.size).isEqualTo(2)
        Truth.assertThat(value[0].id).isEqualTo(testRealEstates[1].id)
        Truth.assertThat(value[0].bitmap).isEqualTo(testPhotos[1].bitmap)
        Truth.assertThat(value[0].type).isEqualTo(testRealEstates[1].type)
        Truth.assertThat(value[0].city).isEqualTo(testRealEstates[1].city)
        Truth.assertThat(value[0].price).isEqualTo("$${testRealEstates[1].price}")


    }

    /*
    @Test
    fun `should not display photo if not found database`() {
        every { photoRepository.getPhoto(1) } returns flowOf()

        val value = getValue(viewModel.uiModelsLiveData)
        Truth.assertThat(value.size).isEqualTo(3)
    }

     */

    private fun assertUiModel(items: List<RealEstateUiModel>, index: Int) {
        Truth.assertThat(items[index].id).isEqualTo(testRealEstates[index].id)
        Truth.assertThat(items[index].bitmap).isEqualTo(testPhotos[index].bitmap)
        Truth.assertThat(items[index].type).isEqualTo(testRealEstates[index].type)
        Truth.assertThat(items[index].city).isEqualTo(testRealEstates[index].city)
        Truth.assertThat(items[index].price).isEqualTo("$${testRealEstates[index].price}")
        //Truth.assertThat(items[index].style).isEqualTo(testRealEstates[index].price)
    }
}