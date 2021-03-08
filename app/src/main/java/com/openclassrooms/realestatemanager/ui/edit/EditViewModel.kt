package com.openclassrooms.realestatemanager.ui.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.ui.ADD_REAL_ESTATE_RESULT_OK
import com.openclassrooms.realestatemanager.ui.EDIT_REAL_ESTATE_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val realEstate = savedStateHandle.get<RealEstate>("realEstate")

    var realEstateType = savedStateHandle.get<String>("realEstateType") ?: realEstate?.type ?: ""
        set(value) {
            field = value
            savedStateHandle.set("realEstateType", value)
        }

    var realEstateCity = savedStateHandle.get<String>("realEstateCity") ?: realEstate?.city ?: ""
        set(value) {
            field = value
            savedStateHandle.set("realEstateCity", value)
        }

    var realEstatePrice = savedStateHandle.get<String>("realEstatePrice") ?: realEstate?.price ?: ""
        set(value) {
            field = value
            savedStateHandle.set("realEstatePrice", value)
        }

    var realEstateDesc =
        savedStateHandle.get<String>("realEstateDesc") ?: realEstate?.description ?: ""
        set(value) {
            field = value
            savedStateHandle.set("realEstateDesc", value)
        }

    private val editRealEstateChannel = Channel<EditRealEstateEvent>()
    val editRealEstateEvent = editRealEstateChannel.receiveAsFlow()

    fun onSaveClick() {
        if (realEstateType.isBlank() ||
            realEstateCity.isBlank() ||
            //realEstatePrice.isBlank() ||
            realEstateDesc.isBlank()
        ) {
            showInvalidInputMessage("Cannot be empty")
            return
        }

        if (realEstate != null) {
            val updatedRealEstate =
                realEstate.copy(type = realEstateType, city = realEstateCity, description = realEstateDesc)
            updateRealEstate(updatedRealEstate)
        } else {
            val newRealEstate = RealEstate(
                type = realEstateType,
                city = realEstateCity,
                price = 300000000f,
                description = realEstateDesc,
                status = false,
                address = "740 Park Avenue, Appt 6/7A, New York, NY 10021, United States",
                surface = 750,
                rooms = 8,
                bathrooms = 2,
                bedrooms = 2,
                nearest = "school",
                agent = "PEACH"
            )
            createRealEstate(newRealEstate)
        }

    }

    private fun createRealEstate(realEstate: RealEstate) = viewModelScope.launch {
        realEstateRepository.insert(realEstate)
        editRealEstateChannel.send(EditRealEstateEvent.NavigateBackResult(ADD_REAL_ESTATE_RESULT_OK))
    }

    private fun updateRealEstate(realEstate: RealEstate) = viewModelScope.launch {
        realEstateRepository.update(realEstate)
        editRealEstateChannel.send(EditRealEstateEvent.NavigateBackResult(EDIT_REAL_ESTATE_RESULT_OK))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        editRealEstateChannel.send(EditRealEstateEvent.ShowInvalidInputMessage(text))
    }

    sealed class EditRealEstateEvent {
        data class ShowInvalidInputMessage(val msg: String) : EditRealEstateEvent()
        data class NavigateBackResult(val result: Int) : EditRealEstateEvent()
    }

}