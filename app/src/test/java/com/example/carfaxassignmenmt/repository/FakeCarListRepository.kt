package com.example.carfaxassignmenmt.repository

import com.example.carfaxassignmenmt.viewmodel.VehicleMockData
import com.example.domain.models.ApiResult
import com.example.domain.models.Vehicle
import com.example.domain.repositories.IVehicleRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCarListRepository(private val isSuccess: Boolean = true) : IVehicleRepository {

	override fun getVehicleList(): Flow<ApiResult<List<Vehicle>>> {
		return flow { emit(if(isSuccess) ApiResult.Success(VehicleMockData.vehicles) else ApiResult.Error(
			NullPointerException("Not Found"))) }
	}

	override fun getVehicle(vin: String): Flow<ApiResult<Vehicle>> {
		return flow {
			VehicleMockData.vehicles.find { it.vin == vin }?.let { emit(ApiResult.Success(it)) }
				?: emit(ApiResult.Error(NullPointerException("No vin found")))
		}
	}


}