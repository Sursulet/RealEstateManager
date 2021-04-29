package com.openclassrooms.realestatemanager.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import com.openclassrooms.realestatemanager.utils.Constants.ADD_REAL_ESTATE_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedRepository: SharedRepository
) : ViewModel() {

    fun onAddNewRealEstateClick() {
        sharedRepository.onAddEvent()
    }

    fun onEditRealEstateClick() {
        sharedRepository.onEditEvent()
    }

    fun onAddResult(result: Int) {
        when(result) {
            ADD_REAL_ESTATE_RESULT_OK -> showRealEstateSavedConfirmation("Real Estate added")
        }
    }

    private fun showRealEstateSavedConfirmation(text: String) = viewModelScope.launch {}


}