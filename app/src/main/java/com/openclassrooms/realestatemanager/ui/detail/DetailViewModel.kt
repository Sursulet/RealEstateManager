package com.openclassrooms.realestatemanager.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.repositories.*
import com.openclassrooms.realestatemanager.utils.Constants.NO_REAL_ESTATE_ID
import com.openclassrooms.realestatemanager.utils.Utils.formattedAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val selectedIdRepository: SelectedIdRepository,
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository,
    private val geocoderRepository: GeocoderRepository
) : ViewModel() {

    val uiModelLiveData = liveData {
        //sharedRepository.realEstateIdState.collect { id ->
        val id = selectedIdRepository.getRealEstateId()
            if (id != NO_REAL_ESTATE_ID) {
                realEstateRepository.getRealEstate(id).collect { realEstate ->
                    photoRepository.getPhotos(realEstate.id).map { photos ->
                        val photosUiModel = photos.map {
                            PhotoUiModel(
                                bitmap = it.bitmap,
                                title = it.title
                            )
                        }

                        realEstate.price?.let {
                            DetailUiModel(
                                id = realEstate.id,
                                type = realEstate.type,
                                city = realEstate.city,
                                price = it,
                                description = realEstate.description,
                                rooms = realEstate.rooms.toString(),
                                bedrooms = realEstate.bedrooms.toString(),
                                bathrooms = realEstate.bathrooms.toString(),
                                surface = realEstate.surface.toString() + " sq m",
                                address = formattedAddress(realEstate.address),
                                status = realEstate.status,
                                nearest = realEstate.nearest,
                                photos = photosUiModel,
                                coordinates = geocoderRepository.getCoordinates(realEstate.address).results?.firstOrNull()?.geometry?.location?.let {
                                                    LatLng(it.lat!!, it.lng!!)
                                                }
                            )
                        }

                    }.collect { detail -> emit(detail) }

                }
            }
        //}
    }

}