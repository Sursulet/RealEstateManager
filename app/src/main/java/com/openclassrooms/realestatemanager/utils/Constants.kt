package com.openclassrooms.realestatemanager.utils

import android.app.Activity
import android.location.Location
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate

object Constants {

    const val TAG = "PEACH" //TODO

    const val DATABASE_NAME = "real_estate_manager_db"

    const val RATE: Double = 0.84

    const val GOOGLE_API_URL = "real_estate_manager_db"

    const val REAL_ESTATE_ID_SAVED_STATE_KEY = "REAL_ESTATE_ID_SAVED_STATE_KEY"
    const val NO_REAL_ESTATE_ID: Long = -1

    const val PERMISSION_READ_KEY = 1001
    const val PERMISSION_WRITE_KEY = 1002
    const val CAMERA_KEY = 1002
    const val IMG_KEY = 110
    const val CAMERA_REQUEST_CODE = 1003
    const val CAMERA_PERMISSION_CODE = 1002

    const val REQUEST_CODE_LOCATION_PERMISSION = 1230

    const val REAL_ESTATE_SAVED_STATE_KEY = "REAL_ESTATE_SAVED_STATE_KEY"
    val NO_REAL_ESTATE = RealEstate(
        type = "",
        city = "",
        price = 0F,
        surface = 0,
        rooms = 0,
        bathrooms = 0,
        bedrooms = 0,
        description = "",
        address = "",
        nearest = "",
        status = false,
        agent = "",
        saleTimestamp = null
    )

    const val ADD_REAL_ESTATE_RESULT_OK = Activity.RESULT_FIRST_USER
    const val EDIT_REAL_ESTATE_RESULT_OK = Activity.RESULT_FIRST_USER + 1

    val PERIODS = listOf("", "days", "weeks", "month", "years")

    val defaultLocation : Location = Location("")
}

//enum class SearchQuery { BY_TYPE, BY_DATE }
//enum class SortOrder { BY_NAME, BY_DATE }