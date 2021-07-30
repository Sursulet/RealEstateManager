package com.openclassrooms.realestatemanager.utils

import android.app.Activity
import android.location.Location
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate

object Constants {

    const val TAG = "PEACH" //TODO

    const val DATABASE_NAME = "real_estate_manager_db"

    const val REQUEST_PERMISSIONS_REQUEST_CODE = 123
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

    fun getRadius(zoomLevel: Float): Double {
        var meters = 0.0
        when(zoomLevel) {
            0f -> meters = 40075017.0
            1f -> meters = 20037508.0
            2f -> meters = 10018754.0
            3f -> meters = 5009377.1
            4f -> meters = 2504688.5
            5f -> meters = 1252344.3
            6f -> meters = 626172.1
            7f -> meters = 313086.1
            8f -> meters = 156543.0
            9f -> meters = 78271.5
            10f -> meters = 39135.8
            11f -> meters = 19567.9
            12f -> meters = 9783.94
            13f -> meters = 4891.97
            14f -> meters = 2445.98
            15f -> meters = 1222.99
            16f -> meters = 611.496
            17f -> meters = 305.748
            18f -> meters = 152.874
            19f -> meters = 76.437
            20f -> meters = 38.2185
            21f -> meters = 19.10926
            22f -> meters = 9.55463
        }

        return meters
    }
}

//enum class SearchQuery { BY_TYPE, BY_DATE }
//enum class SortOrder { BY_NAME, BY_DATE }