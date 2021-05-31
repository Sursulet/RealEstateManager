package com.openclassrooms.realestatemanager.ui.addedit

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SelectedIdRepository
import com.openclassrooms.realestatemanager.utilities.*
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before

import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EditViewModelUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: AddEditViewModel

    private val selectedIdRepository = mockkClass(SelectedIdRepository::class)
    private val realEstateRepository = mockkClass(RealEstateRepository::class)
    private val photoRepository = mockkClass(PhotoRepository::class)
    //@RelaxedMockK private val stateHandler = mockkClass(SavedStateHandle::class)
    private val stateHandler =  mockk<SavedStateHandle>(relaxed = true)

    @Before
    fun setUp() {
        every { selectedIdRepository.selectedId.value } returns 1
        every { realEstateRepository.getRealEstate(1) } returns flowOf(realEstateA)
        every { photoRepository.getPhotos(1) } returns flowOf(testPhotos)

        viewModel = AddEditViewModel(
            selectedIdRepository = selectedIdRepository,
            realEstateRepository = realEstateRepository,
            photoRepository = photoRepository,
            stateHandle = stateHandler
        )
    }

    @Test
    fun test() = runBlockingTest {
        stateHandler["addEditType"] = "zzzz"

        viewModel.uiModel.collect { value ->
            Truth.assertThat(value.type).isEqualTo(realEstateA.type)
        }
        /*
        val value = viewModel.uiModel.value


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

         */
    }
}