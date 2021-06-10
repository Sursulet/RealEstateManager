package com.openclassrooms.realestatemanager.ui.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.repositories.CurrentLocationRepository
import com.openclassrooms.realestatemanager.repositories.GeocoderRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.Utils.getDistance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    realEstateRepository: RealEstateRepository,
    private val currentLocationRepository: CurrentLocationRepository,
    private val geocoderRepository: GeocoderRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<LatLng>>(emptyList())
    val uiState = _uiState.asStateFlow()

    private val realEstates = realEstateRepository.getRealEstates()
    private val currentLocation = currentLocationRepository.lastLocationUpdates
    var radius = MutableStateFlow(500)

    //Display markers located within a radius of 500

    init {
        viewModelScope.launch {
            //Get all realEstate whose distance is less than 500
            realEstates.combine(currentLocation) { estates, lastLocation ->
                estates.mapNotNull { realEstate ->
                    geocoderRepository.getCoordinates(realEstate.address).results?.firstOrNull()?.geometry?.location?.let {
                        LatLng(it.lat!!,it.lng!!)
                    }
                }.filter { getDistance(it.latitude,it.longitude,lastLocation.latitude,lastLocation.longitude) < 500 }
            }.collect {
                _uiState.value = it
            }
        }
    }

}