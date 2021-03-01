package com.openclassrooms.realestatemanager.ui.viewmodels

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.ui.list.RealEstateUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ListRealEstateViewModel @Inject constructor(
    private val repository: RealEstateRepository
) : ViewModel() {

    private val realEstatesFlow = repository.getRealEstates()
    val uiModelsLiveData = liveData {
        realEstatesFlow.collect { realEstates ->
            emit(realEstates.map {
                RealEstateUiModel(
                    id = it.id.toString(),
                    url = it.agent,
                    type = it.type,
                    city = it.city,
                    price = it.price.toString()
                )
            })
        }
    }

}