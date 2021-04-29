package com.openclassrooms.realestatemanager.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.openclassrooms.realestatemanager.data.local.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate


class RealEstateContentProvider /*: ContentProvider()*/ {
/*
    val AUTHORITY = "com.openclassrooms.realestatemanager.provider"
    val TABLE_NAME = RealEstate::class.java.simpleName
    val URI_ITEM = Uri.parse("content://$AUTHORITY/$TABLE_NAME")

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {

        if (context != null) {
            val id: Long = ContentUris.parseId(uri)
            val cursor = RealEstateManagerDatabase.getIntance(context!!).realEstateDao()
                .getItemsWithCursor(id)
            cursor.setNotificationUri(context!!.contentResolver, uri)
            return cursor
        }

        throw IllegalArgumentException("Failed to query row for uri $uri")
    }

    override fun getType(uri: Uri): String? {
        return "vnd.android.cursor.item/$AUTHORITY.$TABLE_NAME"
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        if (context != null && values != null) {
            val id = RealEstateManagerDatabase.getIntance(context!!).realEstateDao()
                .insert(RealEstate.fromContentValues(values))
            if (id != 0L) {
                context!!.contentResolver.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
        }

        throw IllegalArgumentException("Failed to insert row into $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        throw IllegalArgumentException("Failed to delete row into $uri");
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        if (context != null && values != null) {
            val count = RealEstateManagerDatabase.getIntance(context!!).realEstateDao()
                .insert(RealEstate.fromContentValues(values))
            context!!.contentResolver.notifyChange(uri, null)
            return count.toString().toInt()
        }

        throw IllegalArgumentException("Failed to update row into $uri")
    }
*/
}