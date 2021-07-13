package com.openclassrooms.realestatemanager.ui.map

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.repositories.CurrentLocationRepository
import com.openclassrooms.realestatemanager.repositories.GeocoderRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.Constants.TAG
import com.openclassrooms.realestatemanager.utils.Utils.checkNetworkConnection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class MapsViewModel @Inject constructor(
    realEstateRepository: RealEstateRepository,
    currentLocationRepository: CurrentLocationRepository,
    private val geocoderRepository: GeocoderRepository,
    val application: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow<MapsUiState>(MapsUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val realEstates = realEstateRepository.getRealEstates()
    private val currentLocation = currentLocationRepository.lastLocationUpdates
    var radius = MutableStateFlow(200)
    private val networkState = checkNetworkConnection(application)

    //Display markers located within a radius
    init {
        viewModelScope.launch {
            //Get all realEstate whose distance is less than 500
            combine(realEstates,currentLocation,radius, networkState) { estates, lastLocation,radius, networkState ->
                Log.d(TAG, "GPS: $lastLocation")
                if (!networkState) {
                    _uiState.value = MapsUiState.ShowErrorMessage("not connect")
                }
                /*
                val list = estates.mapNotNull { realEstate ->
                    geocoderRepository.getCoordinates(realEstate.address).results?.firstOrNull()?.geometry?.location?.let {
                        LatLng(it.lat!!,it.lng!!)
                    }
                }.filter { getDistance(it.latitude,it.longitude,lastLocation.latitude,lastLocation.longitude) < 500 }

                 */

                val list2 = estates.mapNotNull { realEstate ->
                    geocoderRepository.getCoordinates(realEstate.address).results?.firstOrNull()?.geometry?.location?.let {
                        LatLng(it.lat!!,it.lng!!)
                    }
                }.filter {
                    val location = Location("").apply {
                        latitude = it.latitude
                        longitude = it.longitude
                    }
                    val distance = lastLocation.distanceTo(location)
                    Log.d(TAG, "DISTANCE: $distance")
                    distance / 1000 < radius
                }

                MapsUiModel(
                    lastLocation = LatLng(lastLocation.latitude, lastLocation.longitude),
                    markers = list2
                )
            }.collect {
                _uiState.value = MapsUiState.Available(it)
            }
        }
    }

    sealed class MapsUiState {
        object Empty: MapsUiState()
        data class Available(val mapsUiModel: MapsUiModel): MapsUiState()
        data class ShowErrorMessage(val msg: String): MapsUiState()
    }
}