package com.openclassrooms.realestatemanager.data.local.entities

data class Address (
    val street: String?,
    val extras: String?,
    val city: String,
    val code: String,
    val country: String
)