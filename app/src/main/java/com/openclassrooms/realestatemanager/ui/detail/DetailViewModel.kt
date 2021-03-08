package com.openclassrooms.realestatemanager.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository
) : ViewModel() {

    private val realEstate = realEstateRepository.getRealEstatePending()

    val uiModelLiveData = liveData<DetailUiModel> {
        realEstate.collect{
            emit(DetailUiModel(
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
            ))
        }
    }
}