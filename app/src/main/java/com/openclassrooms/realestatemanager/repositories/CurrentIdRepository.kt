package com.openclassrooms.realestatemanager.repositories

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentIdRepository @Inject constructor() {

    private val _currentId = MutableStateFlow<Long?>(null)
    val currentId: StateFlow<Long?> = _currentId.asStateFlow()

    fun getRealEstateId(): Long? {
        return currentId.value
    }

    fun setRealEstateId(realEstateId: Long) {
        _currentId.value = realEstateId
    }

    fun onAddEvent() {
        _currentId.value = null
    }

    fun onEditEvent() { }

}