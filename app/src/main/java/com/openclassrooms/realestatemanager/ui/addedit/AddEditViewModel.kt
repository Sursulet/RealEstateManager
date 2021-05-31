package com.openclassrooms.realestatemanager.ui.addedit

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.local.entities.Photo
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.repositories.AddEditPhotoRepository
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.repositories.SelectedIdRepository
import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel
import com.openclassrooms.realestatemanager.utils.Constants.NO_REAL_ESTATE_ID
import com.openclassrooms.realestatemanager.utils.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    selectedIdRepository: SelectedIdRepository,
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository,
    private val addEditPhotoRepository: AddEditPhotoRepository,
    private val stateHandle: SavedStateHandle
) : ViewModel() {


    private val _uiState = MutableStateFlow<AddEditUiState>(AddEditUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val _realEstate = MutableStateFlow<RealEstate?>(null)
    val realEstate = _realEstate.asStateFlow()

    private var _photos = MutableStateFlow<List<Photo>>(listOf())
    val photos = _photos.asStateFlow()

    private var _photosUiModel = MutableStateFlow<List<PhotoUiModel>>(listOf())
    private val photosUiModel = _photosUiModel.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedId = selectedIdRepository.selectedId
            val newPhoto = addEditPhotoRepository.photo

            combine(selectedId, newPhoto) { id, photo ->
                if (id == NO_REAL_ESTATE_ID) {

                    _photosUiModel.value = _photosUiModel.value + photo
                    Log.d(TAG, "NEW Ad: ${photosUiModel.value}")
                    //_uiState.value =
                        AddEditUiState.UiModel(AddEditUiModel(photos = photosUiModel.value))

                } else {
                    val estate = realEstateRepository.getRealEstate(id).first()
                    val photos = photoRepository.getPhotos(id).first()

                    _photosUiModel.value = _photosUiModel.value + photo
                    Log.d(TAG, "NEW Ed: ${photosUiModel.value}")

                    val uiModel = AddEditUiModel(
                        photos = photos.map {
                            PhotoUiModel(
                                it.id,
                                it.bitmap,
                                it.title
                            )
                        } + photosUiModel.value,
                        type = stateHandle.get<String>("addEditType") ?: estate.type,
                        price = stateHandle.get<String>("addEditPrice") ?: estate.price.toString(),
                        address = stateHandle.get<String>("addEditAddress") ?: estate.address,
                        city = stateHandle.get<String>("addEditCity") ?: estate.city,
                        state = stateHandle.get<String>("addEditState") ?: estate.city,
                        nearest = stateHandle.get<String>("addEditNearest") ?: estate.nearest,
                        description = stateHandle.get<String>("addEditDesc") ?: estate.description,
                        surface = stateHandle.get<String>("addEditSurface")
                            ?: estate.surface.toString(),
                        rooms = stateHandle.get<String>("addEditRooms") ?: estate.rooms.toString(),
                        bathrooms = stateHandle.get<String>("addEditBath")
                            ?: estate.bathrooms.toString(),
                        bedrooms = stateHandle.get<String>("addEditBed")
                            ?: estate.bedrooms.toString(),
                        status = stateHandle.get<Boolean>("addEditStatus") ?: estate.status,
                        agent = stateHandle.get<String>("addEditAgent") ?: estate.agent
                    )

                    //_uiState.value =
                        AddEditUiState.UiModel(uiModel = uiModel)

                }
            }.collect {
                _uiState.value = it
            }
        }
    }

    //var realEstatePhotos = emptyList<Photo>()
    var realEstateType: String = ""
        set(value) {
            field = value
            stateHandle["addEditType"] = value
        }

    var realEstatePrice: String = ""
        set(value) {
            field = value
            stateHandle["addEditPrice"] = value
        }

    var realEstateAddress: String = ""
        set(value) {
            field = value
            stateHandle["addEditAddress"] = value
        }

    var realEstateCity: String = ""
        set(value) {
            field = value
            stateHandle["addEditCity"] = value
        }
    var realEstateState: String = ""
        set(value) {
            field = value
            stateHandle["addEditState"] = value
        }
    var realEstateAgent: String = ""
        set(value) {
            field = value
            stateHandle["addEditAgent"] = value
        }
    var realEstateDesc: String = ""
        set(value) {
            field = value
            stateHandle["addEditDesc"] = value
        }
    var realEstateSurface: String = ""
        set(value) {
            field = value
            stateHandle["addEditSurface"] = value
        }
    var realEstateRooms: String = ""
        set(value) {
            field = value
            stateHandle["addEditRooms"] = value
        }
    var realEstateBedrooms: String = ""
        set(value) {
            field = value
            stateHandle["addEditBed"] = value
        }
    var realEstateBathrooms: String = ""
        set(value) {
            field = value
            stateHandle["addEditBath"] = value
        }
    var realEstateNearest: String = ""
        set(value) {
            field = value
            stateHandle["addEditNearest"] = value
        }
    var saleTimestamp = null
        set(value) {
            field = value
            stateHandle["addEditTimestamp"] = value
        }
    var realEstateStatus: Boolean = false
        set(value) {
            field = value
            stateHandle["addEditStatus"] = value
        }

    fun onAddEditPhoto(photoUiModel: PhotoUiModel) {
        val b = _photosUiModel.value.contains(photoUiModel)
        Log.d(TAG, "onAddEditPhoto: $photoUiModel // $b")
        _photosUiModel.value = _photosUiModel.value + listOf(photoUiModel)
    }

    fun onSaveClick() {

        //Verify
        var hasError = false
        val errorState = AddEditUiState.ShowInvalidInputMessage(
            typeError = if (realEstateType.isBlank()) {
                hasError = true
                "type is empty "
            } else null,

            addressError = if (realEstateAddress.isBlank()) {
                hasError = true
                "address is empty"
            } else null,
            priceError = if (realEstatePrice.isBlank()) {
                hasError = true
                "price is empty"
            } else null,
            surfaceError = if (realEstateSurface.isBlank()) {
                hasError = true
                "surface is empty"
            } else null,
            agentError = if (realEstateAgent.isBlank()) {
                hasError = true
                "agent is empty"
            } else null
        )

        if (hasError) {
            setErrorMessage(errorState)
            return
        }


        if (selectedId.value == NO_REAL_ESTATE_ID) {
            val newRealEstate = RealEstate(
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
                saleTimestamp = saleTimestamp,
                agent = realEstateAgent
            )

            createRealEstate(newRealEstate)
        } else {
            val updateRealEstate = realEstate.value.copy()
        }
    }

    private fun createRealEstate(realEstate: RealEstate) =
        viewModelScope.launch {
            val newRealEstateId = realEstateRepository.insert(realEstate)
            Log.d(TAG, "createRealEstate: $newRealEstateId")
            val newPhotos = photosUiModel.value.map {
                Photo(title = it.title, bitmap = it.bitmap, realEstateId = newRealEstateId)
            }
            photoRepository.insertPhotos(newPhotos)
            _uiState.value = AddEditUiState.Success
            //_addEditEvent.emit(AddEditRealEstateEvent.NavigateBackResult(Constants.ADD_REAL_ESTATE_RESULT_OK))
        }

    private fun setErrorMessage(errorState: AddEditUiState.ShowInvalidInputMessage) {
        _uiState.value = errorState
    }

    fun clear() {
        addEditPhotoRepository.clear()
    }

    sealed class AddEditUiState {
        object Empty : AddEditUiState()
        data class UiModel(val uiModel: AddEditUiModel) : AddEditUiState()
        data class ShowInvalidInputMessage(
            val typeError: String?,
            val addressError: String?,
            val priceError: String?,
            val surfaceError: String?,
            val agentError: String?
        ) : AddEditUiState()

        object Success : AddEditUiState()
    }
}