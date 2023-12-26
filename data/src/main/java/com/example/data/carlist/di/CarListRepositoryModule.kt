package com.example.data.carlist.di

import com.example.data.carlist.model.db.CarListingLocalSource
import com.example.data.carlist.model.mapper.CarListItemMapper
import com.example.data.carlist.network.CarListingApiService
import com.example.data.carlist.repository.CarListRepository
import com.example.data.database.CarListingDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(ViewModelComponent::class)
object CarListRepositoryModule {

  @Provides
  @ViewModelScoped
  fun provideCarListItemMapper() = CarListItemMapper

  @Provides
  @ViewModelScoped
  fun provideCarListingLocalSource(carListingDatabase: CarListingDatabase)
  = carListingDatabase.getCarListingLocalSource()

  @Provides
  @ViewModelScoped
  fun provideCarListRepository(
    carListingApiService: CarListingApiService, carListingLocalSource: CarListingLocalSource,
    carListItemMapper: CarListItemMapper, dispatcher: CoroutineDispatcher
  ) = CarListRepository(carListingApiService, carListingLocalSource, carListItemMapper, dispatcher)



}