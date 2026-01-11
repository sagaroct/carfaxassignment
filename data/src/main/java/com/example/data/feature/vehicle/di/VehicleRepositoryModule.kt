package com.example.data.feature.vehicle.di

import com.example.data.feature.vehicle.model.db.VehicleDao
import com.example.data.feature.vehicle.repository.VehicleRepository
import com.example.data.database.VehicleDatabase
import com.example.domain.repositories.IVehicleRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VehicleRepositoryModule {

	@Provides
	@Singleton
	fun provideVehicleDao(vehicleDatabase: VehicleDatabase): VehicleDao =
		vehicleDatabase.vehicleDao()
}

@Module
@InstallIn(SingletonComponent::class)
abstract class VehicleRepositoryBinderModule {
	@Binds
	abstract fun bindVehicleRepository(
		vehicleRepository: VehicleRepository
	): IVehicleRepository
}
