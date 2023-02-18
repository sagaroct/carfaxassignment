package com.example.carfaxassignmenmt.di

import com.example.carfaxassignmenmt.data.network.ICarListRepository
import com.example.carfaxassignmenmt.data.repository.CarListRepository
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
    abstract fun bindMovieRepository(
        carListRepository: CarListRepository
    ): ICarListRepository

}
