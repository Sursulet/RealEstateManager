package com.openclassrooms.realestatemanager.ui.edit

import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EditViewModel @AssistedInject constructor(
    @Assisted
    private val isEditing: Boolean,
    private val sharedRepository: SharedRepository,
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {

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
        // Use SingleLiveEvent instead !
        //editRealEstateChannel.send(EditRealEstateEvent.ShowInvalidInputMessage(text))
    }

    sealed class EditRealEstateEvent {
        data class ShowInvalidInputMessage(val msg: String) : EditRealEstateEvent()
        data class NavigateBackResult(val result: Int) : EditRealEstateEvent()
    }
}