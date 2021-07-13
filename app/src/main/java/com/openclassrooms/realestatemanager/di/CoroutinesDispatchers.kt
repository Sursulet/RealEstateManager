package com.openclassrooms.realestatemanager.di

import kotlinx.coroutines.CoroutineDispatcher

data class CoroutinesDispatchers(
    val mainCoroutineDispatcher: CoroutineDispatcher,
    val iOCoroutineDispatcher: CoroutineDispatcher
)
