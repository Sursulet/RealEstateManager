package com.openclassrooms.realestatemanager.repositories

import android.app.Application
import android.content.Context
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentLocationRepository @Inject constructor(
    //val context: Context
    ) {

    private val _lastLocationUpdates: MutableStateFlow<Location> = MutableStateFlow(Location(""))
    val lastLocationUpdates = _lastLocationUpdates.asStateFlow()

    private var isUpdate: Boolean = false

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback

    private fun buildLocationRequest() {
        locationRequest = LocationRequest.create()
            .setInterval(10_000)
            .setFastestInterval(5_000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
    }

    private fun buildLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                _lastLocationUpdates.value = locationResult.lastLocation
            }
        }
    }

    private fun startLocationUpdates() {
        if(!isUpdate){
            isUpdate = true
            //fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

            try {
                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.getMainLooper()
                )
            } catch (e: SecurityException) {
                Log.e("Exception %s: ", e.message!!)
            }
        }
    }

    private fun stopLocationUpdates() {
        isUpdate = false
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}