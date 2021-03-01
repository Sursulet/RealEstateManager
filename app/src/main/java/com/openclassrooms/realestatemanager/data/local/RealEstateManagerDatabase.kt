package com.openclassrooms.realestatemanager.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.utils.Constants.DATABASE_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = [RealEstate::class],
    version = 1,
    exportSchema = false
)
abstract class RealEstateManagerDatabase : RoomDatabase() {

    abstract fun realEstateDao(): RealEstateDao

    companion object {

        @Volatile
        private var INSTANCE: RealEstateManagerDatabase? = null

        fun getInstance(
            context: Context,
            scope: CoroutineScope
        ): RealEstateManagerDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    RealEstateManagerDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(Callback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }


    class Callback(val scope: CoroutineScope) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            INSTANCE?.let { database ->
                scope.launch {
                    val realEstateDao = database.realEstateDao()
                    val realEstateA = RealEstate(
                        1, "house", "city", 1f, 1,
                        2, 2, 2,
                        "It's a great house", "Photo description",
                        "school", false,
                        1, "",
                        "PEACH"
                    )
                    realEstateDao.insert(realEstateA)
                }
            }
        }
    }

}