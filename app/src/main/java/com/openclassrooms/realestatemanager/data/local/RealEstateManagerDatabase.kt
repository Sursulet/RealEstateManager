package com.openclassrooms.realestatemanager.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.realestatemanager.data.local.dao.PhotoDao
import com.openclassrooms.realestatemanager.data.local.dao.RealEstateDao
import com.openclassrooms.realestatemanager.data.local.entities.Photo
import com.openclassrooms.realestatemanager.data.local.entities.RealEstate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(
    entities = [RealEstate::class, Photo::class],
    version = 1,
    exportSchema = false
)
abstract class RealEstateManagerDatabase : RoomDatabase() {

    abstract fun realEstateDao(): RealEstateDao
    abstract fun photoDao(): PhotoDao

    /*
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
                )
                    .fallbackToDestructiveMigration()
                    .addCallback(Callback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

    }

     */


    class Callback @Inject constructor(
        private val database: Provider<RealEstateManagerDatabase>,
        private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val realEstateDao = database.get().realEstateDao()
            val photoDao = database.get().photoDao()

            applicationScope.launch {

                photoDao.insertPhotos(
                    mutableListOf(Photo(
                        id = 1,
                        title = "First",
                        url = "https://cdn.pixabay.com/photo/2016/06/24/10/47/house-1477041_1280.jpg",
                        realEstateId = 1
                    ))
                )
                photoDao.insertPhotos(
                    mutableListOf(Photo(
                        id = 2,
                        title = "Three",
                        url = "https://cdn.pixabay.com/photo/2013/10/09/02/27/lake-192990_1280.jpg",
                        realEstateId = 2
                    ))
                )
                realEstateDao.insert(
                    RealEstate(
                        id = 1,
                        type = "Penthouse",
                        city = "Upper East Side",
                        price = 2987200f,
                        description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean aliquam in ex vitae mollis. Duis semper lobortis gravida. Nam imperdiet sodales nisl. Donec cursus, erat nec fermentum consequat, urna orci blandit enim, at volutpat nunc est non nulla. Nunc commodo blandit neque, lacinia molestie dui pellentesque nec. Proin ultrices ex quis porttitor dignissim. Praesent tempor ex urna, ac accumsan eros finibus sed. Etiam accumsan imperdiet dictum.",
                        status = false,
                        address = "740 Park Avenue, Appt 6/7A, New York, NY 10021, United States",
                        surface = 750,
                        rooms = 8,
                        bathrooms = 2,
                        bedrooms = 2,
                        nearest = "school",
                        agent = "PEACH"
                    )
                )
                realEstateDao.insert(
                    RealEstate(
                        id = 2,
                        type = "House",
                        city = "Southampton",
                        price = 2300000f,
                        description = "Donec vulputate eros sed dictum sodales. Praesent consequat nibh quam, quis euismod odio rutrum in. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Duis faucibus urna et imperdiet luctus. Cras ac libero pretium, cursus eros in, mattis felis. Nam placerat massa in elit laoreet posuere. Praesent auctor justo quis mattis lacinia. Pellentesque efficitur, tortor bibendum molestie vehicula, orci mauris accumsan est, ut vehicula augue lectus sed justo. ",
                        status = false,
                        address = "Chilworth Drove, Chilworth, Southampton, SO16",
                        surface = 800,
                        rooms = 9,
                        bathrooms = 2,
                        bedrooms = 3,
                        nearest = "Swaythling Station, Chandlers Ford Station, Southampton Airport Parkway Station, Oakwood Primary School, Rosewood Free School",
                        agent = "YOSHI"
                    )
                )
            }

        }
    }

}