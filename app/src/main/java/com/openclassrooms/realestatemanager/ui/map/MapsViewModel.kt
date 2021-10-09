package com.openclassrooms.realestatemanager.ui.map

import android.app.Application
import android.location.Location
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.openclassrooms.realestatemanager.repositories.CurrentLocationRepository
import com.openclassrooms.realestatemanager.repositories.GeocoderRepository
import com.openclassrooms.realestatemanager.repositories.LocationPermissionsRepository
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.Constants.TAG
import com.openclassrooms.realestatemanager.utils.Constants.getRadius
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
    private val locationPermissionsRepository: LocationPermissionsRepository,
    private val currentLocationRepository: CurrentLocationRepository,
    private val geocoderRepository: GeocoderRepository,
    application: Application
) : ViewModel() {

    private val _uiState = MutableStateFlow<MapsUiState>(MapsUiState.Empty)
    val uiState = _uiState.asStateFlow()

    private val permissions =
        MutableStateFlow(false)//locationPermissionsRepository.hasLocationPermissions()
    private val realEstates = realEstateRepository.getRealEstates()
    private val currentLocation = currentLocationRepository.lastLocationUpdates
    var zoomLevel = MutableStateFlow(10f)
    private val networkState = checkNetworkConnection(application)//MutableStateFlow(false)

    //Display markers located within a radius
    init {
        viewModelScope.launch {
            permissions.value = locationPermissionsRepository.hasLocationPermissions()

            combine(
                networkState,
                permissions,
                zoomLevel,
                realEstates,
                currentLocation
            ) { connexion, permissions, zoom, estates, lastLocation ->
                if (connexion && permissions) {
                    val list = estates.mapNotNull { realEstate ->
                        geocoderRepository.getCoordinates(realEstate.address.toString())
                            .results?.firstOrNull()?.geometry
                            ?.location?.let {
                                LatLng(it.lat!!, it.lng!!)
                            }
                    }.filter {
                        val radius = getRadius(zoom)
                        val location = Location("").apply {
                            latitude = it.latitude
                            longitude = it.longitude
                        }
                        val distance = lastLocation.distanceTo(location)
                        distance < radius
                    }

                    val uiModel = MapsUiModel(
                        lastLocation = LatLng(lastLocation.latitude, lastLocation.longitude),
                        markers = list
                    )

                    MapsUiState.Available(uiModel)

                } else {
                    MapsUiState.ShowErrorMessage(permissions,connexion)
                }
                /*
                val list = estates.mapNotNull { realEstate ->
                    geocoderRepository.getCoordinates(realEstate.address).results?.firstOrNull()?.geometry?.location?.let {
                        LatLng(it.lat!!,it.lng!!)
                    }
                }.filter { getDistance(it.latitude,it.longitude,lastLocation.latitude,lastLocation.longitude) < 500 }

                 */


            }.collect {
                _uiState.value = it//MapsUiState.Available(it)
            }
            

        }
    }

    fun stopLocationUpdates() {
        currentLocationRepository.stopLocationUpdates()
    }

    /*
        Verify network connection && permissions location
     */
    fun check() = viewModelScope.launch {
        //Log.d(TAG, "check CONNECT: ${networkState.value}")
        /*
        //val b = isInternetAvailable(app)
        combine(permissions,networkState) { permissions, network ->
            Log.d(TAG, "check: (NET) $network || (permissions) $permissions")
            when(permissions && network) {
                true -> openMap() //MapReady
                false -> MapsUiState.ShowErrorMessage(permission = permissions, network = network)
            }
        }.collect {
            _uiState.value = it
            Log.d(TAG, "check: ${uiState.value}")
        }

         */
    }

    private fun openMap(): MapsUiState {
        val uiModel = MapsUiModel(
            lastLocation = LatLng(48.3,2.33),
            markers = emptyList()
        )

        return MapsUiState.Available(uiModel)
    }

    sealed class MapsUiState {
        object Empty : MapsUiState()
        data class Available(val mapsUiModel: MapsUiModel) : MapsUiState()
        data class ShowErrorMessage(val permission: Boolean, val network: Boolean) : MapsUiState()
    }
}