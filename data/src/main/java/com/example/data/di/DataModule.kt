package com.example.data.di

import android.content.Context
import com.example.data.model.local.CarListItemMapper
import com.example.data.network.CarListingApiService
import com.example.data.repository.CarListRepository
import com.example.data.database.CarListingDatabase
import com.example.domain.usecases.GetCarDataUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
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
  fun provideCarListRepository(carListingApiService: CarListingApiService, carListingDatabase: CarListingDatabase,
                                carListItemMapper: CarListItemMapper, dispatcher: CoroutineDispatcher
  ) = CarListRepository(carListingApiService, carListingDatabase, carListItemMapper, dispatcher)


  @Provides
  fun provideGetCarDataUseCase(carListRepository: CarListRepository): GetCarDataUseCase {
    return GetCarDataUseCase(carListRepository)
  }

}