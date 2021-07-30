package com.openclassrooms.realestatemanager.provider

import android.content.ContentResolver
import android.content.ContentUris
import android.content.ContentValues
import android.test.ProviderTestCase2
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.google.common.truth.Truth.assertThat
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
    //private lateinit var resolver: MockContentResolver
    private lateinit var resolver: ContentResolver

    private val estateId: Long = 1
    private val estateId2: Long = 45

    @Before
    override fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RealEstateManagerDatabase::class.java
        ).allowMainThreadQueries().build()
        //resolver = mockContentResolver
        resolver = InstrumentationRegistry.getInstrumentation().context.contentResolver
    }

    @Test
    fun getRealEstateWithSuccess() {
        /*
        val cursor = resolver.query(ContentUris.withAppendedId(RealEstateContentProvider(),estateId),null,null)
        assertThat(cursor).isEqualTo(null)
        assertThat(0).isEqualTo(cursor.count)
        cursor.close

         */
    }

    @Test
    fun insertAndGetItem() {
        /*
        resolver.insert(RealEstateContentProvider(),getEstates())

        val cursor = resolver.query(ContentUris.withAppendedId(RealEstateContentProvider(),estateId),null,null)
        assertThat(cursor)

         */
    }

    private fun getEstates(): ContentValues {
        val values = ContentValues()
        values.put("type","duplex")
        return values
    }
}