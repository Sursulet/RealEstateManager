package com.openclassrooms.realestatemanager.ui.list

import android.util.Log
import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.Constants.ADD_REAL_ESTATE_RESULT_OK
import com.openclassrooms.realestatemanager.utils.Constants.EDIT_REAL_ESTATE_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RealEstatesViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val realEstatesEventChannel = Channel<RealEstatesEvent>()
    val realEstatesEvent = realEstatesEventChannel.receiveAsFlow()

    fun onRealEstateSelected(realEstateId: Int) { realEstateRepository.realEstateIdPending.value = realEstateId }

    private val realEstatesFlow = realEstateRepository.getRealEstates()
    val uiModelsLiveData = liveData {
        realEstatesFlow.collect { realEstates ->
            emit(realEstates.map {
                RealEstateUiModel(
                    id = it.id,
                    url = "it.url",
                    type = it.type,
                    city = it.city,
                    price = "$" + it.price.toString(),
                    style = "#FF4081"
                )
            })
        }
    }

    val testResult = liveData<List<RealEstateUiModel>> {
        val list : MutableList<RealEstateUiModel> = mutableListOf()
        realEstatesFlow.transform { realEstates ->
            realEstates.forEach { realEstate ->
                photoRepository.getPhotos(realEstate.id).collect { photos ->
                    emit(Pair(realEstate, photos))
                }
            }
        }.collect {
            Log.d("PEACH", "${it.first.id}: ${it.second}")
            list.add(RealEstateUiModel(
                it.first.id,
                it.second[0].url,
                it.first.type,
                it.first.city,
                it.first.price.toString(),
                "#FF4081"
            ))
        }

        emit(list)
    }

    fun onAddNewRealEstate() = viewModelScope.launch {
        realEstatesEventChannel.send(RealEstatesEvent.NavigateToAddRealEstateScreen)
    }

    fun onEditRealEstate(realEstate: RealEstate) = viewModelScope.launch {
        realEstatesEventChannel.send(RealEstatesEvent.NavigateToEditRealEstateScreen(realEstate))
    }

    fun onEditResult(result: Int) {
        when(result) {
            ADD_REAL_ESTATE_RESULT_OK -> showRealEstateSavedConfirmationMessage("Real Estate added")
            EDIT_REAL_ESTATE_RESULT_OK -> showRealEstateSavedConfirmationMessage("Real Estate updated")
        }
    }

    private fun showRealEstateSavedConfirmationMessage(text: String) = viewModelScope.launch {
        realEstatesEventChannel.send(RealEstatesEvent.ShowRealEstateSavedConfirmationMessage(text))
    }
    
    sealed class RealEstatesEvent {
        object NavigateToAddRealEstateScreen : RealEstatesEvent()
        data class NavigateToEditRealEstateScreen(val realEstateId: RealEstate) : RealEstatesEvent()
        data class ShowRealEstateSavedConfirmationMessage(val msg: String) : RealEstatesEvent()
    }

}