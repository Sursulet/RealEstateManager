package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.api.GeocoderApiService
import com.openclassrooms.realestatemanager.data.geocoder.GeocoderResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeocoderRepository @Inject constructor(
    private val service: GeocoderApiService
) {

    suspend fun getCoordinates(address: String): GeocoderResponse {
        return service.getCoordinates(address)
    }
}