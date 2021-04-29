package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.utils.Constants.NO_REAL_ESTATE_ID
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedRepository @Inject constructor() {

    private val _realEstateIdState = MutableStateFlow(NO_REAL_ESTATE_ID)
    val realEstateIdState: StateFlow<Int> = _realEstateIdState

    fun getRealEstateId(): Int {
        return realEstateIdState.value
    }

    fun setRealEstateId(realEstateId: Int) {
        _realEstateIdState.value = realEstateId
    }

    fun onAddEvent() {
        _realEstateIdState.value = NO_REAL_ESTATE_ID
    }

    fun onEditEvent() {
        //Log.d("PEACH", "onEditEvent: ${realEstateIdState.value}")
    }

}