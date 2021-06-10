package com.openclassrooms.realestatemanager.repositories

import android.app.Application
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentLocationRepository @Inject constructor(
    val application: Application
) {

    private val _lastLocationUpdates: MutableStateFlow<Location?> = MutableStateFlow(null)
    val lastLocationUpdates : Flow<Location> = _lastLocationUpdates.filterNotNull()

    private var isInitialized = AtomicBoolean(false)

    private val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(application)

    private val locationCallback: LocationCallback by lazy {
        object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                _lastLocationUpdates.value = locationResult.lastLocation
            }
        }
    }

    fun startLocationUpdates() {
        if (isInitialized.compareAndSet(false, true)) {
            try {
                fusedLocationClient.requestLocationUpdates(
                    LocationRequest.create()
                        .setInterval(10_000)
                        .setFastestInterval(5_000)
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY),
                    locationCallback,
                    Looper.getMainLooper()
                )
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }

    fun stopLocationUpdates() {
        isInitialized.set(false)
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}