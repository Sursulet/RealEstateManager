package com.openclassrooms.realestatemanager.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.realestatemanager.repositories.SelectedIdRepository
import com.openclassrooms.realestatemanager.repositories.TwoPaneRepository
import com.openclassrooms.realestatemanager.utils.Constants.ADD_REAL_ESTATE_RESULT_OK
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val selectedIdRepository: SelectedIdRepository,
    private val twoPaneRepository: TwoPaneRepository
) : ViewModel() {

    fun onAddNewRealEstateClick() {
        selectedIdRepository.onAddEvent()
    }

    fun onEditRealEstateClick() {
        selectedIdRepository.onEditEvent()
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


}