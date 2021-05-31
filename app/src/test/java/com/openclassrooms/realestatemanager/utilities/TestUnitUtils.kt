package com.openclassrooms.realestatemanager.utilities

import com.openclassrooms.realestatemanager.data.local.entities.Photo
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.ui.detail.PhotoUiModel
import io.mockk.mockk

val realEstateA = RealEstate(
    id = 1,
    type = "flat",
    city = "PARIS",
    price = 7.1f,
    surface = 0,
    rooms = 0,
    bedrooms = 0,
    bathrooms = 0,
    description = "Large kitchen, Ocean View",
    address = "",
    nearest = "Ocean",
    status = false,
    saleTimestamp = null,
    agent = "PEACH"
)
val realEstateB = RealEstate(
    id = 2,
    type = "Penthouse",
    city = "New-York",
    price = 27.1f,
    surface = 0,
    rooms = 0,
    bedrooms = 0,
    bathrooms = 0,
    description = "Large kitchen, Ocean View",
    address = "",
    nearest = "Ocean",
    status = false,
    saleTimestamp = null,
    agent = "PEACH"
)
val realEstateC = RealEstate(
    id = 3,
    type = "House",
    city = "Bordeaux",
    price = 37.1f,
    surface = 0,
    rooms = 0,
    bedrooms = 0,
    bathrooms = 0,
    description = "Large kitchen, Ocean View",
    address = "",
    nearest = "Ocean",
    status = false,
    saleTimestamp = null,
    agent = "PEACH"
)

val photoA = Photo(id = 1, bitmap = mockk(), title = "", realEstateId = 1)
val photoB = Photo(id = 2, bitmap = mockk(), title = "", realEstateId = 2)
val photoC = Photo(id = 3, bitmap = mockk(), title = "", realEstateId = 3)

val testRealEstates = arrayListOf(
    realEstateA,
    realEstateB,
    realEstateC
)

val testPhotos = arrayListOf(
    photoA,
    photoB,
    photoC
    /*
    Photo(id = 1, bitmap = mockk(), title = "Kitchen", realEstateId = 1),
    Photo(id = 2, bitmap = mockk(), title = "Garden", realEstateId = 1),
    Photo(id = 3, bitmap = mockk(), title = "Room", realEstateId = 1)

     */
)

val testUiModelPhotos = arrayListOf(
    PhotoUiModel(bitmap = mockk(), title = "Kitchen"),
    PhotoUiModel(bitmap = mockk(), title = "Garden"),
    PhotoUiModel(bitmap = mockk(), title = "Room")
)