package com.openclassrooms.realestatemanager.ui.edit

import android.util.Log
import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import com.openclassrooms.realestatemanager.utils.Utils.splitAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val stateHandle: SavedStateHandle,
    //private val isEditing: Boolean,
    private val sharedRepository: SharedRepository,
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val isEdit = stateHandle.get<Boolean>("EXTRA_IS_EDITING")
    private val id = if (isEdit == true) sharedRepository.realEstateIdState.value else null
    private var realEstate = if (id != null) realEstateRepository.getRealEstate(id) else null

    val uiLiveData = liveData {
        realEstate?.collect {
            emit(
                EditUiModel(
                    type = it.type,
                    price = it.price.toString(),
                    address = it.address,
                    city = it.city,
                    state = splitAddress(it.address).last(),
                    description = it.description,
                    agent = it.agent
                )
            )
        }
    }

    /*
    companion object {
        fun provideFactory(
            assistedFactory: AssistedFactory,
            isEditing: Boolean
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(isEditing) as T
            }
        }
    }

    @dagger.assisted.AssistedFactory
    interface AssistedFactory {
        fun create(isEditing: Boolean): EditViewModel
    }

    @ExperimentalCoroutinesApi
    val liveData : LiveData<RealEstate> = liveData {
        if (isEditing) {
            sharedRepository.realEstateIdState.filterNotNull().flatMapLatest {
                realEstateRepository.getRealEstate(it)
            }.collect {
                emit(it)
            }
        }
    }

     */

    var realEstateType: String = ""
    var realEstatePrice: String = ""
    var realEstateAddress: String = ""
    var realEstateCity: String = ""
    var realEstateState: String = ""
    var realEstateAgent: String = ""
    var realEstateDesc: String = ""
    var realEstateSurface: String = ""
    var realEstateRooms: String = ""
    var realEstateBedrooms: String = ""
    var realEstateBathrooms: String = ""
    var realEstateNearest: String = ""

    fun onSaveClick() {
        Log.d("PEACH", "onSaveClick: $realEstateType")
        if (
            realEstateType.isBlank() ||
            realEstatePrice.isBlank() ||
            realEstateCity.isBlank() ||
            realEstateAddress.isBlank() ||
            realEstateState.isBlank() ||
            realEstateAgent.isBlank() ||
            realEstateDesc.isBlank() ||
            realEstateSurface.isBlank() ||
            realEstateRooms.isBlank() ||
            realEstateBedrooms.isBlank() ||
            realEstateBathrooms.isBlank() ||
            realEstateNearest.isBlank()
        ) {
            Log.d("PEACH", "onSaveClick: Name cannot be empty")
            return
        }

        if (realEstate != null) {
            //val updateRealEstate = realEstate.copy
        } else {
            val newRealEstate = RealEstate(
                //id = ,
                type = realEstateType,
                description = realEstateDesc,
                city = realEstateCity,
                price = realEstatePrice.toFloat(),
                surface = realEstateSurface.toInt(),
                rooms = realEstateRooms.toInt(),
                bedrooms = realEstateBedrooms.toInt(),
                bathrooms = realEstateBathrooms.toInt(),
                address = realEstateAddress,
                nearest = realEstateNearest,
                agent = realEstateAgent
            )
        }
    }

    private fun createRealEstate(realEstate: RealEstate) = viewModelScope.launch {
        realEstateRepository.insert(realEstate)
        //editRealEstateChannel.send(EditRealEstateEvent.NavigateBackResult(ADD_REAL_ESTATE_RESULT_OK))
    }

    private fun updateRealEstate(realEstate: RealEstate) = viewModelScope.launch {
        realEstateRepository.update(realEstate)
        //editRealEstateChannel.send(EditRealEstateEvent.NavigateBackResult(EDIT_REAL_ESTATE_RESULT_OK))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        // Use SingleLiveEvent instead !
        //editRealEstateChannel.send(EditRealEstateEvent.ShowInvalidInputMessage(text))
    }

    sealed class EditRealEstateEvent {
        data class ShowInvalidInputMessage(val msg: String) : EditRealEstateEvent()
        data class NavigateBackResult(val result: Int) : EditRealEstateEvent()
    }
}