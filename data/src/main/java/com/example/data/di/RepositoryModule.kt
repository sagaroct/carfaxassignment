package com.example.data.di

import com.example.data.repository.CarListRepository
import com.example.domain.repositories.ICarListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by Sagar Pujari on 24/01/23.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCarListRepository(
        carListRepository: CarListRepository
    ): ICarListRepository


}
