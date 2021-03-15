package com.openclassrooms.realestatemanager.ui.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.Constants.ADD_REAL_ESTATE_RESULT_OK
import com.openclassrooms.realestatemanager.utils.Constants.EDIT_REAL_ESTATE_RESULT_OK
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

    var realEstateAddress =
        savedStateHandle.get<String>("realEstateAddress") ?: realEstate?.address ?: ""
        set(value) {
            field = value
            savedStateHandle.set("realEstateAddress", value)
        }

    var realEstateCity = savedStateHandle.get<String>("realEstateCity") ?: realEstate?.city ?: ""
        set(value) {
            field = value
            savedStateHandle.set("realEstateCity", value)
        }

    var realEstatePrice = savedStateHandle.get<Float>("realEstatePrice") ?: realEstate?.price ?: 0f
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

    var realEstateStatus =
        savedStateHandle.get<Boolean>("realEstateStatus") ?: realEstate?.status ?: false
        set(value) {
            field = value
            savedStateHandle.set("realEstateStatus", value)
        }

    var realEstateAgent =
        savedStateHandle.get<String>("realEstateAgent") ?: realEstate?.agent ?: ""
        set(value) {
            field = value
            savedStateHandle.set("realEstateAgent", value)
        }

    private val editRealEstateChannel = Channel<EditRealEstateEvent>()
    val editRealEstateEvent = editRealEstateChannel.receiveAsFlow()

    fun onSaveClick() {
        if (realEstateType.isBlank() ||
            realEstateAddress.isBlank() ||
            realEstateCity.isBlank() ||
            //realEstatePrice.isBlank() ||
            realEstateDesc.isBlank() ||
            realEstateAgent.isBlank()
        ) {
            showInvalidInputMessage("Cannot be empty")
            return
        }

        if (realEstate != null) {
            val updatedRealEstate =
                realEstate.copy(
                    type = realEstateType,
                    address = realEstateAddress,
                    city = realEstateCity,
                    description = realEstateDesc,
                    status = realEstateStatus,
                    agent = realEstateAgent
                )
            updateRealEstate(updatedRealEstate)
        } else {
            val newRealEstate = RealEstate(
                type = realEstateType,
                city = realEstateCity,
                price = realEstatePrice,
                description = realEstateDesc,
                status = realEstateStatus,
                address = "740 Park Avenue, Appt 6/7A, New York, NY 10021, United States",
                surface = 750,
                rooms = 8,
                bathrooms = 2,
                bedrooms = 2,
                nearest = "school",
                agent = realEstateAgent
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