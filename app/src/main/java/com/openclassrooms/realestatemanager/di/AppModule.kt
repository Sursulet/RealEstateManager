package com.openclassrooms.realestatemanager.di

import android.app.Application
import androidx.room.Room
import com.openclassrooms.realestatemanager.data.local.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        app: Application,
        callback: RealEstateManagerDatabase.Callback
    ) = Room.databaseBuilder(app, RealEstateManagerDatabase::class.java, DATABASE_NAME)
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideRealEstateDao(database: RealEstateManagerDatabase) = database.realEstateDao()

    @Provides
    fun providePhotoDao(database: RealEstateManagerDatabase) = database.photoDao()

    @Singleton
    @Provides
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())

    @Singleton
    @Provides
    fun provideCoroutineDispatchers() = CoroutinesDispatchers(Dispatchers.Main,Dispatchers.IO)
}
/*
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

 */