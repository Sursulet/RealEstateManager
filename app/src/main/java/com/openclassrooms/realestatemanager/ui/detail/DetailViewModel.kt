package com.openclassrooms.realestatemanager.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.repositories.CurrentIdRepository
import com.openclassrooms.realestatemanager.repositories.GeocoderRepository
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.Utils.formattedAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val currentIdRepository: CurrentIdRepository,
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository,
    private val geocoderRepository: GeocoderRepository
) : ViewModel() {

    val uiModelLiveData = liveData {
        //sharedRepository.realEstateIdState.collect { id ->
        val id = currentIdRepository.getRealEstateId()
            if (id != null) {
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