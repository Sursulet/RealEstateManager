package com.openclassrooms.realestatemanager.provider

import android.test.ProviderTestCase2
import android.test.mock.MockContentResolver
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.openclassrooms.realestatemanager.data.local.RealEstateManagerDatabase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class RealEstateContentProviderTest : ProviderTestCase2<RealEstateContentProvider>(
    RealEstateContentProvider::class.java,
    "com.openclassrooms.realestatemanager.provider"
) {

    private lateinit var db: RealEstateManagerDatabase
    private lateinit var resolver: MockContentResolver

    @Before
    override fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealEstateManagerDatabase::class.java
        ).allowMainThreadQueries().build()
        resolver = mockContentResolver
    }

    @Test
    fun getRealEstateWithSuccess() {
        //val cursor: Cursor = resolver.query()
    }
}