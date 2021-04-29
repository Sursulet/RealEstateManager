package com.openclassrooms.realestatemanager.utilities

import com.openclassrooms.realestatemanager.data.local.entities.Photo
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel

val realEstateA = RealEstate(id = 1, type = "flat", city = "PARIS", price = 7.1f, surface = 0, rooms = 0, bedrooms = 0, bathrooms = 0, description = "Large kitchen, Ocean View", address = "", nearest = "Ocean", status = false, agent = "PEACH")
val realEstateB = RealEstate(id = 2, type = "Penthouse", city = "New-York", price = 27.1f, surface = 0, rooms = 0, bedrooms = 0, bathrooms = 0, description = "Large kitchen, Ocean View", address = "", nearest = "Ocean", status = false, agent = "PEACH")
val realEstateC = RealEstate(id = 3, type = "House", city = "Bordeaux", price = 37.1f, surface = 0, rooms = 0, bedrooms = 0, bathrooms = 0, description = "Large kitchen, Ocean View", address = "", nearest = "Ocean", status = false, agent = "PEACH")

val photoA = Photo(id = 1, url = "", title = "", realEstateId = 1)
val photoB = Photo(id = 2, url = "", title = "", realEstateId = 1)
val photoC = Photo(id = 3, url = "", title = "", realEstateId = 1)

val testRealEstates = arrayListOf(
    realEstateA,
    realEstateB,
    realEstateC
)

val testPhotos = arrayListOf(
    Photo(id = 1, url = "", title = "", realEstateId = 1),
    Photo(id = 2, url = "", title = "", realEstateId = 1),
    Photo(id = 3, url = "", title = "", realEstateId = 1)
)

val testUiModelPhotos = arrayListOf(
    PhotoUiModel(id = "1", url = "", name = ""),
    PhotoUiModel(id = "2", url = "", name = ""),
    PhotoUiModel(id = "3", url = "", name = "")
)