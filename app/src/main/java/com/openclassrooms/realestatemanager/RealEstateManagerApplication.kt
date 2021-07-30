package com.openclassrooms.realestatemanager

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class RealEstateManagerApplication : Application() {
    companion object {
        lateinit var instance: Application
    }

    init {
        instance = this
    }
}