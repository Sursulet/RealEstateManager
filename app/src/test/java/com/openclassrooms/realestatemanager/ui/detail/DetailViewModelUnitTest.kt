package com.openclassrooms.realestatemanager.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.android.gms.maps.model.LatLng
import com.google.common.truth.Truth
import com.openclassrooms.realestatemanager.data.geocoder.GeocoderResponse
import com.openclassrooms.realestatemanager.data.geocoder.Geometry
import com.openclassrooms.realestatemanager.data.geocoder.Location
import com.openclassrooms.realestatemanager.data.geocoder.Result
import com.openclassrooms.realestatemanager.repositories.*
import com.openclassrooms.realestatemanager.utilities.*
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockkClass
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Before

import org.junit.Rule
import org.junit.Test

class DetailViewModelUnitTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: DetailViewModel

    private val currentIdRepository = mockkClass(CurrentIdRepository::class)
    private val realEstateRepository = mockkClass(RealEstateRepository::class)
    private val photoRepository = mockkClass(PhotoRepository::class)
    private val geocoderRepository = mockkClass(GeocoderRepository::class)

    private val id: Int = 1

    @Before
    fun setUp() {
        every { currentIdRepository.getRealEstateId() } returns 1
        every { realEstateRepository.getRealEstate(1) } returns flowOf(realEstateA)
        every { photoRepository.getPhotos(1) } returns flowOf(testPhotos)
        coEvery { geocoderRepository.getCoordinates(realEstateA.address) } returns getDefaultGeocoderResponse()

        viewModel = DetailViewModel(
            currentIdRepository = currentIdRepository,
            realEstateRepository = realEstateRepository,
            photoRepository = photoRepository,
            geocoderRepository = geocoderRepository
        )
    }

    private fun getDefaultGeocoderResponse(): GeocoderResponse {

        return GeocoderResponse().apply {
            results = listOf(Result().apply {
                geometry = Geometry().apply {
                    location = Location().apply {
                        lat = 48.2
                        lng = 2.6
                    }
                }
            })
        }
    }

    @Test
    fun test() {

        val value = getValue(viewModel.uiModelLiveData)

        Truth.assertThat(value?.id).isEqualTo(1)
        Truth.assertThat(value?.type).isEqualTo(realEstateA.type)
        Truth.assertThat(value?.city).isEqualTo(realEstateA.city)
        Truth.assertThat(value?.price).isEqualTo(realEstateA.price)
        Truth.assertThat(value?.surface).isEqualTo("${realEstateA.surface} sq m")
        Truth.assertThat(value?.rooms).isEqualTo(realEstateA.rooms.toString())
        Truth.assertThat(value?.bedrooms).isEqualTo(realEstateA.bedrooms.toString())
        Truth.assertThat(value?.bathrooms).isEqualTo(realEstateA.bathrooms.toString())
        Truth.assertThat(value?.description).isEqualTo(realEstateA.description)
        //Truth.assertThat(value.address).isEqualTo(realEstateA.address) address formatted
        Truth.assertThat(value?.nearest).isEqualTo(realEstateA.nearest)
        Truth.assertThat(value?.status).isEqualTo(realEstateA.status)
        //Truth.assertThat(value?.photos).isEqualTo(testUiModelPhotos)
        Truth.assertThat(value?.coordinates).isEqualTo(LatLng(48.2,2.6))

        //coVerified
    }
}