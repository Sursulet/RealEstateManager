package com.openclassrooms.realestatemanager.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.repositories.CurrentIdRepository
import com.openclassrooms.realestatemanager.repositories.CurrentLocationRepository
import com.openclassrooms.realestatemanager.repositories.TwoPaneRepository
import com.openclassrooms.realestatemanager.utils.Constants.ADD_REAL_ESTATE_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currentIdRepository: CurrentIdRepository,
    private val twoPaneRepository: TwoPaneRepository,
    private val currentLocationRepository: CurrentLocationRepository
) : ViewModel() {

    fun onAddNewRealEstateClick() {
        currentIdRepository.onAddEvent()
    }

    fun onEditRealEstateClick() {
        currentIdRepository.onEditEvent()
    }

    fun onAddResult(result: Int) {
        when(result) {
            ADD_REAL_ESTATE_RESULT_OK -> showRealEstateSavedConfirmation("Real Estate added")
        }
    }

    private fun showRealEstateSavedConfirmation(text: String) = viewModelScope.launch {}
    fun setTwoPane(twoPane: Boolean) {
        twoPaneRepository.set(twoPane)
    }

    fun startLocationUpdates() {
        currentLocationRepository.startLocationUpdates()
    }

    fun stopLocationUpdates() {
        currentLocationRepository.stopLocationUpdates()
    }


}