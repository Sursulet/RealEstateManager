package com.openclassrooms.realestatemanager.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import com.openclassrooms.realestatemanager.ui.list.RealEstateUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val realEstateRepository: RealEstateRepository
) : ViewModel() {

    val uiModelLiveData = liveData {
        sharedRepository.realEstateIdState.collect { id ->
            id?.let { x ->
                realEstateRepository.getRealEstate(x).collect {
                    emit(
                        DetailUiModel(
                            id = it.id,
                            type = it.type,
                            city = it.city,
                            price = it.price,
                            description = it.description,
                            rooms = it.rooms.toString(),
                            bedrooms = it.bedrooms.toString(),
                            bathrooms = it.bathrooms.toString(),
                            surface = it.surface.toString() + "sq m",
                            address = it.address,
                            status = it.status,
                            nearest = it.nearest
                        )
                    )
                }
            }
        }
    }

}