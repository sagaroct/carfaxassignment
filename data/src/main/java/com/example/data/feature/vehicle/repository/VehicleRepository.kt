package com.example.data.feature.vehicle.repository

import com.example.data.feature.vehicle.model.db.VehicleDao
import com.example.data.feature.vehicle.model.mapper.VehicleMapper
import com.example.data.feature.vehicle.model.remote.RemoteVehicle
import com.example.data.feature.vehicle.network.VehicleApiService
import com.example.domain.models.ApiResult
import com.example.domain.models.Vehicle
import com.example.domain.repositories.IVehicleRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class VehicleRepository @Inject constructor(
	private val vehicleApiService: VehicleApiService,
	private val vehicleDao: VehicleDao,
	private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : IVehicleRepository {

	override fun getVehicleList() = flow<ApiResult<List<Vehicle>>> {
		// 1) Check current DB state
		val cached = vehicleDao.get().first()
		val hasCache = cached.isNotEmpty()

		if (hasCache) {
			emit(ApiResult.Success(VehicleMapper.DbToUiMapper.map(cached)))
		}

		// 2) Always refresh from network; DB updates will drive subsequent UI updates
		val remote = vehicleApiService.getVehicles()
		saveCarListings(remote.listings)

		// 3) If there was no cache, wait for DB to be populated then emit it
		if (!hasCache) {
			val populated = vehicleDao.get().first()
			emit(ApiResult.Success(VehicleMapper.DbToUiMapper.map(populated)))
		}
	}
		.catch { exception -> emit(ApiResult.Error(exception)) }
		.flowOn(dispatcher)

	override fun getVehicle(vin: String): Flow<ApiResult<Vehicle>> {
		return vehicleDao.get(vin)
			.map { ApiResult.Success(VehicleMapper.DbToUiMapper.map(it)) }
			.catch { exception -> ApiResult.Error(exception) }
			.flowOn(dispatcher)
	}

	private suspend fun saveCarListings(vehicles: List<RemoteVehicle>): List<Vehicle> {
		vehicleDao.upsert(VehicleMapper.RemoteToDbMapper.map(vehicles))
		return getLocalCarList()
	}

	private suspend fun getLocalCarList(): List<Vehicle> {
		val dbList = vehicleDao.get().firstOrNull() ?: emptyList()
		return VehicleMapper.DbToUiMapper.map(dbList)
	}

}