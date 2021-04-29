package com.openclassrooms.realestatemanager.ui.edit

import android.util.Log
import androidx.lifecycle.*
import com.openclassrooms.realestatemanager.data.local.entities.Photo
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel
import com.openclassrooms.realestatemanager.utils.Constants.ADD_REAL_ESTATE_RESULT_OK
import com.openclassrooms.realestatemanager.utils.Constants.EDIT_REAL_ESTATE_RESULT_OK
import com.openclassrooms.realestatemanager.utils.Constants.NO_REAL_ESTATE_ID
import com.openclassrooms.realestatemanager.utils.Constants.TAG
import com.openclassrooms.realestatemanager.utils.Utils.splitAddress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject constructor(
    sharedRepository: SharedRepository,
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository
) : ViewModel() {

    private val _addEditUiState = MutableStateFlow<AddEditUiState>(AddEditUiState.Empty)
    private val addEditUiState = _addEditUiState.asStateFlow()

    private val _addEditEvent = MutableSharedFlow<AddEditRealEstateEvent>()
    val addEditEvent = _addEditEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            sharedRepository.realEstateIdState.collect { id ->
                if (id != NO_REAL_ESTATE_ID) {
                    val realEstate = realEstateRepository.getRealEstate(id).first()
                    val photos = photoRepository.getPhotos(realEstate.id).first()

                    _addEditUiState.value = AddEditUiState.Edit(realEstate, photos)
                    //_addEditUiState.value = AddEditUiState.Edit
                }
            }
        }
    }

    val uiLiveData = liveData {
        addEditUiState.collect { state ->
            if (state is AddEditUiState.Edit) {
                val realEstate = state.realEstate
                val photos = state.photos
                val uiModel =
                    EditUiModel(
                        photos =
                        photos.map {
                            PhotoUiModel(
                                id = it.id.toString(),
                                url = it.url,
                                name = it.title
                            )
                        },
                        type = realEstate.type,
                        price = realEstate.price.toString(),
                        address = realEstate.address,
                        city = realEstate.city,
                        state = splitAddress(realEstate.address).last(),
                        nearest = realEstate.nearest,
                        description = realEstate.description,
                        surface = realEstate.surface.toString(),
                        rooms = realEstate.rooms.toString(),
                        bathrooms = realEstate.bathrooms.toString(),
                        bedrooms = realEstate.bedrooms.toString(),
                        status = realEstate.status,
                        agent = realEstate.agent
                    )

                emit(uiModel)
            }
        }

    }

    //Valeur du Ui Model
    var realEstatePhotos: MutableList<PhotoUiModel> = mutableListOf()
        //emptyList() //mutableListOf(PhotoUiModel(id = Math.random().toString(), url = "https://cdn.pixabay.com/photo/2016/11/29/03/53/house-1867187_1280.jpg", name = "House"))
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
    var realEstateStatus: Boolean = false

    fun onSaveClick() {
        val state = addEditUiState.value
        /*
        if(realEstatePhotos.isEmpty()) {
            showInvalidInputMessage("Photos cannot be empty")
            return
        }

         */

        if (realEstateType.isBlank()) {
            showInvalidInputMessage("onSaveClick: Type cannot be empty")
            return
        }

        if (realEstateCity.isBlank() || realEstateAddress.isBlank() || realEstateState.isBlank()
        ) {
            showInvalidInputMessage("onSaveClick: Address cannot be empty")
            return
        }

        if (realEstateSurface.isBlank()) {
            showInvalidInputMessage("onSaveClick: Surface cannot be empty")
            return
        }

        if (realEstateDesc.isBlank()) {
            showInvalidInputMessage("onSaveClick: Description cannot be empty")
            return
        }

        if (realEstateAgent.isBlank()) {
            showInvalidInputMessage("onSaveClick: Agent cannot be empty")
            return
        }

        when (state) {
            is AddEditUiState.Edit -> {
                val realEstate = state.realEstate
                val photos = state.photos

                val updateRealEstate = realEstate.copy(
                    //id = ,
                    type = realEstateType,
                    description = realEstateDesc,
                    city = realEstateCity,
                    price = realEstatePrice.toFloatOrNull(),
                    surface = realEstateSurface.toInt(),
                    rooms = realEstateRooms.toIntOrNull(),
                    bedrooms = realEstateBedrooms.toIntOrNull(),
                    bathrooms = realEstateBathrooms.toIntOrNull(),
                    address = "$realEstateAddress, $realEstateCity, $realEstateState",
                    nearest = realEstateNearest,
                    agent = realEstateAgent
                )

                val updatePhotos = photos.map {
                    it.copy()
                }

                Log.d(TAG, "onSaveClick: $updateRealEstate")

                //val updatePhoto = //

                //updateRealEstate(updateRealEstate)
            }
            is AddEditUiState.Empty -> {
                val newRealEstate = RealEstate(
                    //id = ,
                    type = realEstateType,
                    description = realEstateDesc,
                    city = realEstateCity,
                    price = realEstatePrice.toFloatOrNull(),
                    surface = realEstateSurface.toInt(),
                    rooms = realEstateRooms.toIntOrNull(),
                    bedrooms = realEstateBedrooms.toIntOrNull(),
                    bathrooms = realEstateBathrooms.toIntOrNull(),
                    address = "$realEstateAddress, $realEstateCity, $realEstateState",
                    nearest = realEstateNearest,
                    agent = realEstateAgent
                )

                val newPhotos = mutableListOf<Photo>()
                for (photoUiModel in realEstatePhotos) {
                    val photo = Photo(
                        title = photoUiModel.name,
                        url = photoUiModel.url,
                        realEstateId = newRealEstate.id
                    )
                    newPhotos.add(photo)
                }

                createRealEstate(newRealEstate)
                createPhotos(newPhotos)
            }
        }
    }

    private fun createRealEstate(realEstate: RealEstate) = viewModelScope.launch {
        val newRealEstateId = realEstateRepository.insert(realEstate)
        //photoRepository.insertPhotos(mutableListOf(Photo(title = "House", url = "https://cdn.pixabay.com/photo/2016/11/29/03/53/house-1867187_1280.jpg", realEstateId = newRealEstateId)))
        _addEditEvent.emit(AddEditRealEstateEvent.NavigateBackResult(ADD_REAL_ESTATE_RESULT_OK))
    }

    private fun createPhotos(photos: List<Photo>) =
        viewModelScope.launch { photoRepository.insertPhotos(photos) }

    private fun updateRealEstate(realEstate: RealEstate) = viewModelScope.launch {
        realEstateRepository.update(realEstate)
        _addEditEvent.emit(AddEditRealEstateEvent.NavigateBackResult(EDIT_REAL_ESTATE_RESULT_OK))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        // Use SingleLiveEvent instead !
        _addEditEvent.emit(AddEditRealEstateEvent.ShowInvalidInputMessage(text))
    }

    fun addPhoto(photo: PhotoUiModel) {
        realEstatePhotos.add(photo)
        Log.d(TAG, "addPhoto: $realEstatePhotos")
    }

    sealed class AddEditUiState {
        data class Edit(val realEstate: RealEstate, val photos: List<Photo>) : AddEditUiState() //params EditUiModel ?
        object Success : AddEditUiState()
        data class Error(val msg: String) : AddEditUiState()
        //²object Edit : AddEditUiState()
        object Empty : AddEditUiState()
    }

    sealed class AddEditRealEstateEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditRealEstateEvent() //Error
        data class NavigateBackResult(val result: Int) : AddEditRealEstateEvent() //Success
    }
}