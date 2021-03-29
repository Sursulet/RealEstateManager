package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedRepository @Inject constructor(
    private val realEstateDao: RealEstateDao
) {

    private val _realEstateIdState = MutableStateFlow<Int?>(null) // -1
    val realEstateIdState: StateFlow<Int?> = _realEstateIdState

    private val _events = MutableStateFlow<Int?>(null)
    val events : StateFlow<Int?> = _events

    fun setRealEstateId(realEstateId: Int) { _realEstateIdState.value = realEstateId }

    fun onAddEvent() { _events.value = null }

    fun onEditEvent() { _events.value = _realEstateIdState.value }

}