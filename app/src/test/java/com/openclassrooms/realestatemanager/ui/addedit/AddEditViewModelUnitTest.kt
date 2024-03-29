package com.openclassrooms.realestatemanager.ui.addedit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.openclassrooms.realestatemanager.data.local.entities.Address
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.repositories.CurrentIdRepository
import com.openclassrooms.realestatemanager.repositories.CurrentPhotoRepository
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.utilities.*
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDate

@ExperimentalCoroutinesApi
class EditViewModelUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val currentIdRepository = mockkClass(CurrentIdRepository::class)
    private val realEstateRepository = mockkClass(RealEstateRepository::class)
    private val photoRepository = mockkClass(PhotoRepository::class)
    private val currentPhotoRepository = mockkClass(CurrentPhotoRepository::class)

    @Before
    fun setUp() {
        every { currentIdRepository.currentId } returns MutableStateFlow<Long?>(null)
        every { realEstateRepository.getRealEstate(1) } returns flowOf(realEstateA)
        every { photoRepository.getPhotos(1) } returns flowOf(testPhotos)
        every { currentPhotoRepository.photo } returns MutableStateFlow(testUiModelPhotos)
        coEvery {
            realEstateRepository.insert(
                RealEstate(
                    id = 0,
                    type = "House",
                    price = 123000000f,
                    surface = 234,
                    rooms = 5,
                    bathrooms = 2,
                    bedrooms = 3,
                    description = "",
                    address = Address("16 Rue Auguste Perret", null, "Paris", "75013", "France"),
                    nearest = "school",
                    status = false,
                    created = LocalDate.now(),
                    saleTimestamp = null,
                    agent = "Peach"
                )
            )
        } returns 0
        coJustRun {
            realEstateRepository.update(any())
        }
        coJustRun { photoRepository.insertPhoto(any()) }
    }

    @Test
    fun `display add mode`() = mainCoroutineRule.runBlockingTest {
        val viewModel = AddEditViewModel(
            currentIdRepository = currentIdRepository,
            realEstateRepository = realEstateRepository,
            photoRepository = photoRepository,
            currentPhotoRepository = currentPhotoRepository,
            ioDispatcher = mainCoroutineRule.testCoroutineDispatcher
        )

        val state =
            AddEditViewModel.AddEditUiState.Content(
                AddEditUiModel(
                    photos = testUiModelPhotos,
                    type = "",
                    price = "",
                    surface = "",
                    rooms = "",
                    description = "",
                    street = "",
                    extras = "",
                    city = "",
                    code = "",
                    nearest = "",
                    status = false,
                    agent = "",
                )
            )

        assertThat(viewModel.uiState.value).isEqualTo(state)
    }

    @Test
    fun `empty fields`() = mainCoroutineRule.runBlockingTest {
        val viewModel = AddEditViewModel(
            currentIdRepository = currentIdRepository,
            realEstateRepository = realEstateRepository,
            photoRepository = photoRepository,
            currentPhotoRepository = currentPhotoRepository,
            ioDispatcher = mainCoroutineRule.testCoroutineDispatcher
        )

        viewModel.realEstateType = ""
        viewModel.realEstatePrice = ""
        viewModel.realEstateStreet = ""
        viewModel.realEstateExtras = ""
        viewModel.realEstateCity = ""
        viewModel.realEstateCode = ""
        viewModel.realEstateCountry = ""
        viewModel.realEstateAgent = ""
        viewModel.realEstateDesc = ""
        viewModel.realEstateSurface = ""
        viewModel.realEstateRooms = ""
        viewModel.realEstateBedrooms = ""
        viewModel.realEstateBathrooms = ""
        viewModel.realEstateNearest = ""
        viewModel.realEstateStatus = false

        viewModel.onSaveClick()

        val state =
            AddEditViewModel.AddEditUiState.ShowInvalidInputMessage(
                null,
                "type is empty",
                "street is empty",
                "extras is empty",
                "city is empty",
                "code is empty",
                "country is empty",
                "price is empty",
                "surface is empty",
                "agent is empty"
            )
        assertThat(viewModel.uiState.value).isEqualTo(state)
    }

    @Test
    fun `insert a real estate`() = mainCoroutineRule.testCoroutineDispatcher.runBlockingTest {
        val viewModel = AddEditViewModel(
            currentIdRepository = currentIdRepository,
            realEstateRepository = realEstateRepository,
            photoRepository = photoRepository,
            currentPhotoRepository = currentPhotoRepository,
            ioDispatcher = mainCoroutineRule.testCoroutineDispatcher
        )

        viewModel.realEstateType = "House"
        viewModel.realEstatePrice = "123000000"
        viewModel.realEstateStreet = "16 Rue Auguste Perret"
        viewModel.realEstateExtras = "x"
        viewModel.realEstateCity = "Paris"
        viewModel.realEstateCode = "75013"
        viewModel.realEstateCountry = "France"
        viewModel.realEstateAgent = "Peach"
        viewModel.realEstateDesc = ""
        viewModel.realEstateSurface = "234"
        viewModel.realEstateRooms = "5"
        viewModel.realEstateBedrooms = "3"
        viewModel.realEstateBathrooms = "2"
        viewModel.realEstateNearest = "school"
        viewModel.realEstateStatus = false

        viewModel.onSaveClick()

        val state = AddEditViewModel.AddEditUiState.Success("RealEstate is add")

        assertThat(viewModel.uiState.value).isEqualTo(state)

        coVerify(exactly = 1) {
            realEstateRepository.insert(
                RealEstate(
                    id = 0,
                    type = "House",
                    price = 123000000f,
                    surface = 234,
                    rooms = 5,
                    bathrooms = 2,
                    bedrooms = 3,
                    description = "",
                    address = Address("16 Rue Auguste Perret", null, "Paris", "75013", "France"),
                    nearest = "school",
                    status = false,
                    created = LocalDate.now(),
                    saleTimestamp = null,
                    agent = "Peach"
                )
            )
        }

        confirmVerified(realEstateRepository)
    }

    @Test
    fun `update real estate`() = mainCoroutineRule.testCoroutineDispatcher.runBlockingTest {
        every { currentIdRepository.currentId } returns MutableStateFlow(1)

        val viewModel = AddEditViewModel(
            currentIdRepository = currentIdRepository,
            realEstateRepository = realEstateRepository,
            photoRepository = photoRepository,
            currentPhotoRepository = currentPhotoRepository,
            ioDispatcher = mainCoroutineRule.testCoroutineDispatcher
        )

        viewModel.realEstateType = "House"
        viewModel.realEstatePrice = "123000000"
        viewModel.realEstateStreet = "16 Rue Auguste Perret"
        viewModel.realEstateExtras = "x"
        viewModel.realEstateCity = "Paris"
        viewModel.realEstateCode = "75013"
        viewModel.realEstateCountry = "France"
        viewModel.realEstateAgent = "Peach"
        viewModel.realEstateDesc = ""
        viewModel.realEstateSurface = "234"
        viewModel.realEstateRooms = "5"
        viewModel.realEstateBedrooms = "3"
        viewModel.realEstateBathrooms = "2"
        viewModel.realEstateNearest = "school"
        viewModel.realEstateStatus = false

        viewModel.onSaveClick()

        val state = AddEditViewModel.AddEditUiState.Success("RealEstate is update")

        assertThat(viewModel.uiState.value).isEqualTo(state)

        coVerify(exactly = 1) {
            realEstateRepository.getRealEstate(1)
            realEstateRepository.update(RealEstate(
                id = 1,
                type = "House",
                price = 123000000f,
                surface = 234,
                rooms = 5,
                bathrooms = 2,
                bedrooms = 3,
                description = "",
                address = Address("16 Rue Auguste Perret", null, "Paris", "75013", "France"),
                nearest = "school",
                status = false,
                created = LocalDate.now(),
                saleTimestamp = null,
                agent = "Peach"
            ))
        }

        confirmVerified(realEstateRepository)
    }
}