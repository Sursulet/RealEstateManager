package com.openclassrooms.realestatemanager.ui.addedit

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.data.local.entities.Photo
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.repositories.CurrentIdRepository
import com.openclassrooms.realestatemanager.repositories.CurrentPhotoRepository
import com.openclassrooms.realestatemanager.repositories.PhotoRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel
import com.openclassrooms.realestatemanager.utils.Constants.TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    currentIdRepository: CurrentIdRepository,
    private val realEstateRepository: RealEstateRepository,
    private val photoRepository: PhotoRepository,
    private val currentPhotoRepository: CurrentPhotoRepository
) : ViewModel() {

    private val realEstateId = currentIdRepository.currentId.value
    private val newPhoto = currentPhotoRepository.photo

    private val _realEstate = MutableStateFlow<RealEstate?>(null)
    private val realEstate = _realEstate.asStateFlow()

    private var _uiPhotos = MutableStateFlow<List<PhotoUiModel>>(listOf())
    private val uiPhotos = _uiPhotos.asStateFlow()

    private val _uiState = MutableStateFlow<AddEditUiState>(AddEditUiState.Empty) //Content
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _realEstate.value =
                realEstateId?.let { id -> realEstateRepository.getRealEstate(id).first() }

            _realEstate.combine(newPhoto) { estate, newPhoto ->
                val photos = if (estate?.id != null) {
                    photoRepository.getPhotos(estate.id).first()
                } else {
                    emptyList()
                }

                _uiPhotos.value = _uiPhotos.value + newPhoto

                val uiPhotos = photos.map {
                    PhotoUiModel(
                        it.id,
                        it.bitmap,
                        it.title
                    )
                } + _uiPhotos.value

                AddEditUiModel(
                    photos = uiPhotos,
                    type = estate?.type ?: "",
                    price = estate?.price?.toString() ?: "",
                    address = estate?.address ?: "",
                    city = estate?.city ?: "",
                    state = estate?.city ?: "",
                    nearest = estate?.nearest ?: "",
                    description = estate?.description ?: "",
                    surface = estate?.surface?.toString() ?: "",
                    rooms = estate?.rooms?.toString() ?: "",
                    bathrooms = estate?.bathrooms?.toString() ?: "",
                    bedrooms = estate?.bedrooms?.toString() ?: "",
                    status = estate?.status ?: false,
                    agent = estate?.agent ?: ""
                )
            }.collect {
                _uiState.value = AddEditUiState.Content(it)
            }
        }
    }

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
    var saleTimestamp = null
    var realEstateStatus: Boolean = false

    fun onSaveClick() {
        var hasError = false
        val errorState = AddEditUiState.ShowInvalidInputMessage(
            photosError = if (realEstateId == null && uiPhotos.value.isEmpty()) {
                hasError = true
                "Add one photo"
            } else null,
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
            _uiState.value = errorState
            return
        }
        
        if (realEstateId != null) {
            val updateRealEstate = realEstate.value!!.copy(
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

            updateRealEstate(updateRealEstate)
        } else {
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
        }
    }

    private fun updateRealEstate(realEstate: RealEstate) =
        viewModelScope.launch {
            realEstateRepository.update(realEstate)
            createPhotos(realEstate.id, uiPhotos.value)

            _uiState.value = AddEditUiState.Success("RealEstate is update")
        }

    private fun createRealEstate(realEstate: RealEstate) =
        viewModelScope.launch {
            val newRealEstateId = realEstateRepository.insert(realEstate)
            createPhotos(newRealEstateId, uiPhotos.value)
            _uiState.value = AddEditUiState.Success("RealEstate is add")
        }

    private fun createPhotos(id: Long, photos: List<PhotoUiModel>) = viewModelScope.launch {
        photos.map {
            val photo = Photo(title = it.title,bitmap = it.bitmap,realEstateId = id)
            photoRepository.insertPhoto(photo)
        }
    }

    fun clear() {
        currentPhotoRepository.clear()
    }

    sealed class AddEditUiState {
        object Empty : AddEditUiState()
        data class Content(val uiModel: AddEditUiModel) : AddEditUiState()
        data class ShowInvalidInputMessage(
            val photosError: String?,
            val typeError: String?,
            val addressError: String?,
            val priceError: String?,
            val surfaceError: String?,
            val agentError: String?
        ) : AddEditUiState()

        data class Success(val msg: String) : AddEditUiState()
    }
}