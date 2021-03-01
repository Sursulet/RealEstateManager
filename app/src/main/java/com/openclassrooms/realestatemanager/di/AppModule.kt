package com.openclassrooms.realestatemanager.di

import android.content.Context
import androidx.room.Room
import com.openclassrooms.realestatemanager.data.local.RealEstateDao
import com.openclassrooms.realestatemanager.data.local.RealEstateManagerDatabase
import com.openclassrooms.realestatemanager.repositories.RealEstateRepository
import com.openclassrooms.realestatemanager.utils.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
//@InstallIn(ApplicationComponentManager::class)
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        coroutineScope: CoroutineScope
    ) = RealEstateManagerDatabase.getInstance(context, coroutineScope)

    @Provides
    fun provideRealEstateDao(database: RealEstateManagerDatabase) = database.realEstateDao()

    @Singleton
    @Provides
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}
/*
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

 */