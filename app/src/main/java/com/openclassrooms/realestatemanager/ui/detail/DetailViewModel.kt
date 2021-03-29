package com.openclassrooms.realestatemanager.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import com.openclassrooms.realestatemanager.utils.Utils.formattedAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    val uiModelLiveData = liveData {
        sharedRepository.realEstateIdState.collect { id ->
            id?.let { x ->
                realEstateRepository.getRealEstate(x).collect { realEstate ->
                    photoRepository.getPhotos(realEstate.id).map { photos ->
                        val photosUiModel = photos.map {
                            PhotoUiModel(
                                id = it.id.toString(),
                                url = it.url,
                                name = it.title
                            )
                        }

                        DetailUiModel(
                            id = realEstate.id,
                            type = realEstate.type,
                            city = realEstate.city,
                            price = realEstate.price,
                            description = realEstate.description,
                            rooms = realEstate.rooms.toString(),
                            bedrooms = realEstate.bedrooms.toString(),
                            bathrooms = realEstate.bathrooms.toString(),
                            surface = realEstate.surface.toString() + "sq m",
                            address = formattedAddress(realEstate.address),
                            status = realEstate.status,
                            nearest = realEstate.nearest,
                            photos = photosUiModel,
                            coordinates = LatLng(48.86,2.35)//getCoordinates(context, realEstate.address)
                        )

                    }.collect { detail -> emit(detail) }

                }
            }
        }
    }

}