package com.example.data.carlist.di

import com.example.data.carlist.repository.CarListRepository
import com.example.domain.repositories.ICarListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ICarListRepositoryModule {

  @Binds
  abstract fun bindCarListRepository(
    carListRepository: CarListRepository
  ): ICarListRepository

}