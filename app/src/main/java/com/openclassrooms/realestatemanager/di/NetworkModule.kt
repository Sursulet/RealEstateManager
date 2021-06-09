package com.openclassrooms.realestatemanager.di

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.openclassrooms.realestatemanager.api.GeocoderApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Singleton
    @Provides
    fun providesGeocoderService(): GeocoderApiService {
        return GeocoderApiService.create()
    }

    @Singleton
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext app: Context
    ) = FusedLocationProviderClient(app)
}