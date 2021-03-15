package com.openclassrooms.realestatemanager.utils

import android.app.Activity

object Constants {

    const val DATABASE_NAME = "real_estate_manager_db"

    const val ADD_REAL_ESTATE_RESULT_OK = Activity.RESULT_FIRST_USER
    const val EDIT_REAL_ESTATE_RESULT_OK = Activity.RESULT_FIRST_USER + 1
}

enum class SortOrder { BY_NAME, BY_DATE }