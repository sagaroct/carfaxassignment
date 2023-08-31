package com.example.data.carlist.di

import com.example.data.carlist.repository.CarListRepository
import com.example.domain.usecases.GetCarItemUseCase
import com.example.domain.usecases.GetCarListItemUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(ViewModelComponent::class)
object CarListUseCaseModule {

  @Provides
  @ViewModelScoped
  fun provideGetCarItemUseCase(carListRepository: CarListRepository): GetCarItemUseCase {
    return GetCarItemUseCase(carListRepository)
  }

  @Provides
  @ViewModelScoped
  fun provideGetCarListItemUseCase(carListRepository: CarListRepository): GetCarListItemUseCase {
    return GetCarListItemUseCase(carListRepository)
  }

}