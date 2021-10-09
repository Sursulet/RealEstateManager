package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import androidx.room.Room
import com.openclassrooms.realestatemanager.data.local.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.data.local.dao.PhotoDao
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.local.entities.Photo
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import com.openclassrooms.realestatemanager.utils.Constants.AUTHORITY
import com.openclassrooms.realestatemanager.utils.Constants.DATABASE_NAME
import com.openclassrooms.realestatemanager.utils.Constants.TABLE_ESTATE_NAME
import com.openclassrooms.realestatemanager.utils.Constants.TABLE_PHOTO_NAME


class RealEstateContentProvider : ContentProvider() {



    private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
        addURI(AUTHORITY, TABLE_ESTATE_NAME, 1)
        addURI(AUTHORITY, "$TABLE_ESTATE_NAME/#", 2)
        addURI(AUTHORITY, TABLE_PHOTO_NAME, 3)
    }

    private lateinit var appDatabase: RealEstateManagerDatabase
    private var realEstateDao: RealEstateDao? = null
    private var photoDao: PhotoDao? = null

    override fun onCreate(): Boolean {
        appDatabase = Room.databaseBuilder(context!!, RealEstateManagerDatabase::class.java, DATABASE_NAME).build()
        realEstateDao = appDatabase.realEstateDao()
        photoDao = appDatabase.photoDao()
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return when(sUriMatcher.match(uri)) {
            1 -> {
                realEstateDao?.getRealEstatesWithCursor()
            }
            2 -> {
                val id = uri.lastPathSegment!!.toLong()
                realEstateDao?.getRealEstateWithCursor(id)
            }
            3 -> {
                val id = uri.lastPathSegment!!.toLong()
                photoDao?.getPhotosWithCursor(id)
            }
            else -> throw IllegalArgumentException()
        }
    }

    // TODO : not finished
    override fun getType(uri: Uri): String? = when(sUriMatcher.match(uri)) {
        1 -> "vnd.android.cursor.item/$AUTHORITY.$TABLE_ESTATE_NAME"
        2 -> "vnd.android.cursor.item/$AUTHORITY."
        3 -> "vnd.android.cursor.item/$AUTHORITY.$TABLE_PHOTO_NAME"
        else -> null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        return null
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

}