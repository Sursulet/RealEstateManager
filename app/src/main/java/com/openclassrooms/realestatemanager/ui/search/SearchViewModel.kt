package com.openclassrooms.realestatemanager.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val realEstateRepository: RealEstateRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {


    var searchPriceQuery = savedStateHandle.getLiveData<String>("searchQuery","")

    //var priceMin = savedStateHandle.get<String>("priceMin") ?: real

    fun onSearch() {}
}