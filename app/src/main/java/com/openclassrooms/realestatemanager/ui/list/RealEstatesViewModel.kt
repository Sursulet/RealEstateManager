package com.openclassrooms.realestatemanager.ui.list

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class RealEstatesViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val realEstatesFlow = realEstateRepository.getRealEstates()
    /*
    val uiModelsLiveData = liveData {
        realEstatesFlow.collect { realEstates ->
            emit(realEstates.map {
                RealEstateUiModel(
                    id = it.id,
                    url = "it.url",
                    type = it.type,
                    city = it.city,
                    price = "$" + it.price.toString(),
                    style = "#FF4081"
                )
            })
        }
    }

     */

    val uiModelsLiveData = liveData {
        realEstatesFlow.collect { list ->
            val uiModels = list.map {
                photoRepository.getPhotos(it.id)
                    .map { value ->
                        RealEstateUiModel(
                            id = it.id,
                            url = value[0].url,
                            type = it.type,
                            city = it.city,
                            price = "$" + it.price.toString(),
                            style = "#FF4081"
                        )
                    }.first()
            }

            emit(uiModels)
        }
    }

    fun onRealEstateSelected(realEstateId: Int) {
        sharedRepository.setRealEstateId(realEstateId)
    }
}