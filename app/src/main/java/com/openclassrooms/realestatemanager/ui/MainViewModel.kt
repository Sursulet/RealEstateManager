package com.openclassrooms.realestatemanager.ui

import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.repositories.SharedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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

}