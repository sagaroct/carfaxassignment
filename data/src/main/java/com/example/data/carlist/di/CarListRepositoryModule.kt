package com.example.data.carlist.di

import com.example.data.carlist.network.CarListingApiService
import com.example.data.carlist.repository.CarListRepository
import com.example.data.database.CarListingDatabase
import com.example.data.model.local.CarListItemMapper
import com.example.domain.repositories.ICarListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object CarListRepositoryModule {

  @Provides
  fun provideCarListItemMapper() = CarListItemMapper

  @Provides
  fun provideCarListRepository(carListingApiService: CarListingApiService, carListingDatabase: CarListingDatabase,
                               carListItemMapper: CarListItemMapper, dispatcher: CoroutineDispatcher
  ) = CarListRepository(carListingApiService, carListingDatabase, carListItemMapper, dispatcher)

}