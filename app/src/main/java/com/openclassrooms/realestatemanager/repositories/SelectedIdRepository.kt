package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.utils.Constants.NO_REAL_ESTATE_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectedIdRepository @Inject constructor() {

    private val _selectedId = MutableStateFlow<Long>(NO_REAL_ESTATE_ID)
    val selectedId: StateFlow<Long> = _selectedId.asStateFlow()

    fun getRealEstateId(): Long {
        return selectedId.value
    }

    fun setRealEstateId(realEstateId: Long) {
        _selectedId.value = realEstateId
    }

    fun onAddEvent() {
        _selectedId.value = NO_REAL_ESTATE_ID
    }

    fun onEditEvent() {
        //Log.d("PEACH", "onEditEvent: ${selectedId.value}")
    }

}