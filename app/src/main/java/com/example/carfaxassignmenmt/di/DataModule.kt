package com.example.carfaxassignmenmt.di

import android.content.Context
import com.example.carfaxassignmenmt.data.database.CarListingDatabase
import com.example.carfaxassignmenmt.data.model.local.CarListItemMapper
import com.example.carfaxassignmenmt.data.network.CarListingApiService
import com.example.carfaxassignmenmt.data.repository.CarListRepository
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
  fun providesMovieRepository(movieApiService: CarListingApiService, movieDatabase: CarListingDatabase,
                              carListItemMapper: CarListItemMapper, dispatcher: CoroutineDispatcher
  ) = CarListRepository(movieApiService, movieDatabase, carListItemMapper, dispatcher)

}