package com.openclassrooms.realestatemanager.repositories

import com.openclassrooms.realestatemanager.data.geocoder.Result
import com.openclassrooms.realestatemanager.api.GeocoderApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GeocoderRepository @Inject constructor(
    private val service: GeocoderApiService
) {

    fun getCoordinates(address: String): Flow<Result>? {
        return null
        /*
        return try {
            val response = service.getCoordinates(address)
            if (response.isSuccessful) {
                response?.let {
                    return@let //succes
                } ?: //error
            } else {
                //error
            }
        } catch (e: Exception) {
            //error
        }

         */
    }
}