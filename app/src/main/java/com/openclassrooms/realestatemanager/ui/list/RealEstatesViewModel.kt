package com.openclassrooms.realestatemanager.ui.list

import android.util.Log
import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.repositories.CurrentSearchParametersRepository
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
    private val photoRepository: PhotoRepository,
    currentSearchParametersRepository: CurrentSearchParametersRepository
) : ViewModel() {

    private val realEstatesFlow = realEstateRepository.getRealEstates()
    private val searchQuery = currentSearchParametersRepository.current
    val value = if(searchQuery.value == null) realEstatesFlow else searchQuery

    val uiModelsLiveData = liveData {
        value.collect { list ->
            val uiModels = list?.map {
                val photoURl = photoRepository.getPhoto(it.id).firstOrNull()
                RealEstateUiModel(
                    id = it.id,
                    url = photoURl?.url,
                    type = it.type,
                    city = it.city,
                    price = "$" + it.price.toString(),
                    style = "#FF4081"
                )
            }

            emit(uiModels)
        }
    }
    /*
    val uiModelsLiveData: LiveData<List<RealEstateUiModel>> = liveData {
        realEstatesFlow.collect { list ->
            val uiModels = list.map {
                val photoURl = photoRepository.getPhoto(it.id).firstOrNull()
                Log.d(TAG, "DATE: ${it.createdDateFormatted}")
                RealEstateUiModel(
                    id = it.id,
                    url = photoURl?.url,
                    type = it.type,
                    city = it.city,
                    price = "$" + it.price.toString(),
                    style = "#FF4081"
                )
            }

            emit(uiModels)
        }
    }

     */

    fun onRealEstateSelected(realEstateId: Int) {
        sharedRepository.setRealEstateId(realEstateId)
    }
}