package com.openclassrooms.realestatemanager.ui.edit

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    private val sharedRepository: SharedRepository,
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var isEditing: Boolean? = null
    lateinit var liveData : LiveData<RealEstate>

    fun init(isEditing: Boolean) {
        this.isEditing = isEditing
        if (isEditing) {
            liveData = liveData {
                sharedRepository.realEstateIdState.transform {
                    it?.let { id ->
                        realEstateRepository.getRealEstate(id).collect { re ->
                            emit(re)
                        }
                    }
                }.collect { emit(it) }
            }
        } else {
            liveData = MutableLiveData()
        }
    }


    private val editRealEstateChannel = Channel<EditRealEstateEvent>()
    val editRealEstateEvent = editRealEstateChannel.receiveAsFlow()

    fun onSaveClick() {


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
        editRealEstateChannel.send(EditRealEstateEvent.ShowInvalidInputMessage(text))
    }

    sealed class EditRealEstateEvent {
        data class ShowInvalidInputMessage(val msg: String) : EditRealEstateEvent()
        data class NavigateBackResult(val result: Int) : EditRealEstateEvent()
    }

}