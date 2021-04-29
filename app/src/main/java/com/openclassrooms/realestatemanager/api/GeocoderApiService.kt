package com.openclassrooms.realestatemanager.api

import com.openclassrooms.realestatemanager.data.geocoder.GeocoderResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GeocoderApiService {

    @GET("maps/api/geocode/json?")
    suspend fun getCoordinates(
        @Query("address") address: String,
        @Query("key") key: String = "" //TODO : BuildConfig.GOOGLE_MAPS_KEY
    ): GeocoderResponse

    companion object {
        private const val BASE_URL = "https://maps.googleapis.com/"

        fun create(): GeocoderApiService {
            val logger = HttpLoggingInterceptor().apply { level =
                HttpLoggingInterceptor.Level.BASIC
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GeocoderApiService::class.java)
        }
    }
}