package com.openclassrooms.realestatemanager.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
    private val stateHandle: SavedStateHandle
) : ViewModel() {

    /*
    private val searchQuery = MutableStateFlow("")

    private val realEstatesFlow = searchQuery.flatMapLatest {
        realEstateRepository.getRealEstates()
    }

     */
}