package com.openclassrooms.realestatemanager.di

import com.openclassrooms.realestatemanager.api.GeocoderApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesGeocoderService(): GeocoderApiService {
        return GeocoderApiService.create()
    }
}