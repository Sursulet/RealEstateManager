package com.openclassrooms.realestatemanager.repositories

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TwoPaneRepository @Inject constructor() {
    private val _twoPane = MutableStateFlow(false)
    val twoPane : Flow<Boolean> = _twoPane.asStateFlow()

    fun set(twoPane: Boolean) {
        _twoPane.value = twoPane
    }
}