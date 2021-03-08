package com.openclassrooms.realestatemanager.ui.list

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealEstatesViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val realEstates = realEstateRepository.getRealEstates().asLiveData()

    /*
    private val realEstatesFlow = realEstateRepository.getRealEstates()
    val uiLiveData = liveData {
        realEstatesFlow.collect { realEstatesList ->
            emit(
                realEstates.map {
                    RealEstateUiModel(
                        id = it.id.toString(),
                        url = it.agent,
                        type = it.type,
                        city = it.city,
                        price = it.price.toString
                    )
                }
            )
        }
    }

     */
    //val realEstatesUiModel: LiveData<List<RealEstateUiModel>> = realEstateRepository.getRealEstates().asLiveData()

    private fun deleteAll() = viewModelScope.launch {
        realEstateRepository.deleteAll()
    }

    fun setRealEstateIdQuery(realEstateId: Int) {
        realEstateRepository.realEstateIdPending.value = realEstateId
    }

    private val realEstatesFlow = realEstateRepository.getRealEstates()
    val uiModelsLiveData = liveData {
        realEstatesFlow.collect { realEstates ->
            emit(realEstates.map {
                RealEstateUiModel(
                    id = it.id,
                    url = "it.url",
                    type = it.type,
                    city = it.city,
                    price = "$" + it.price.toString()
                )
            })
        }
    }

    //val photoUI = photoRepository.getPhoto(1).asLiveData()
}