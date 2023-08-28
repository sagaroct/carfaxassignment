package com.example.data.di

import android.content.Context
import com.example.data.model.local.CarListItemMapper
import com.example.data.network.CarListingApiService
import com.example.data.repository.CarListRepository
import com.example.data.database.CarListingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

  @Provides
  @Singleton
  fun provideAppDatabase(@ApplicationContext appContext: Context): CarListingDatabase {
    return CarListingDatabase.get(appContext)
  }

  @Provides
  fun provideCarListItemMapper() = CarListItemMapper

  @Provides
  fun provideDispatcher() = Dispatchers.IO

  @Provides
  fun providesCarListRepository(carListingApiService: CarListingApiService, carListingDatabase: CarListingDatabase,
                                carListItemMapper: CarListItemMapper, dispatcher: CoroutineDispatcher
  ) = CarListRepository(carListingApiService, carListingDatabase, carListItemMapper, dispatcher)

}