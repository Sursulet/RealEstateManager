package com.openclassrooms.realestatemanager.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.repositories.CurrentIdRepository
import com.openclassrooms.realestatemanager.repositories.GeocoderRepository
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.Utils.formattedAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val currentIdRepository: CurrentIdRepository,
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository,
    private val geocoderRepository: GeocoderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DetailUiModel?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            currentIdRepository.currentId.collect { id ->
                if (id != null) {
                    realEstateRepository.getRealEstate(id).collect { realEstate ->
                        photoRepository.getPhotos(realEstate.id).map { photos ->
                            val photosUiModel = photos.map {
                                PhotoUiModel(
                                    bitmap = it.bitmap,
                                    title = it.title
                                )
                            }

                            DetailUiModel(
                                id = realEstate.id,
                                type = realEstate.type,
                                city = realEstate.address.city,
                                price = 234f,//realEstate.price!!,
                                description = realEstate.description,
                                rooms = realEstate.rooms.toString(),
                                bedrooms = realEstate.bedrooms.toString(),
                                bathrooms = realEstate.bathrooms.toString(),
                                surface = realEstate.surface.toString() + " sq m",
                                address = "${realEstate.address.street} ${realEstate.address.code} ${realEstate.address.city} ${realEstate.address.country}",//formattedAddress(realEstate.address),
                                status = realEstate.status,
                                nearest = realEstate.nearest,
                                photos = photosUiModel,
                                coordinates = null /*geocoderRepository.getCoordinates(realEstate.address)
                                    .results?.firstOrNull()?.geometry
                                    ?.location?.let { location ->
                                        LatLng(location.lat!!, location.lng!!)
                                    }*/
                            )


                        }.collect { detail ->
                            _uiState.value = detail
                        }

                    }
                }
            }
        }
    }

    sealed class DetailUiState {
        data class Success(val uiModel: DetailUiModel) : DetailUiState()
    }
}