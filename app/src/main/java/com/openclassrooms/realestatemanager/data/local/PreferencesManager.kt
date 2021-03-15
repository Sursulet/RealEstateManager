package com.openclassrooms.realestatemanager.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {

    //private val dataStore = context.createDataStore("user_preferences")
    /*
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    val preferencesFlow = context.dataStore.data
        .map { preferences ->
            val sortOrder = SortOrder.valueOf(
                preferences[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_DATE.name
            )
        }

    private object PreferencesKeys {
        val SORT_ORDER = preferencesKey<String>("sort_order")
    }

     */
}