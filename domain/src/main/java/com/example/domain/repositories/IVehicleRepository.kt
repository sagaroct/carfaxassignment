package com.example.domain.repositories

import com.example.domain.models.ApiResult
import com.example.domain.models.Vehicle
import kotlinx.coroutines.flow.Flow

interface IVehicleRepository {

    fun getVehicleList(): Flow<ApiResult<List<Vehicle>>>

    fun getVehicle(vin: String): Flow<ApiResult<Vehicle>>

}