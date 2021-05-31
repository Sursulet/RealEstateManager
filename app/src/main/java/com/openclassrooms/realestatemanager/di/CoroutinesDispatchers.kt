package com.openclassrooms.realestatemanager.di

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.MainCoroutineDispatcher

data class CoroutinesDispatchers(
    val mainCoroutineDispatcher: CoroutineDispatcher,
    val iOCoroutineDispatcher: CoroutineDispatcher
)
