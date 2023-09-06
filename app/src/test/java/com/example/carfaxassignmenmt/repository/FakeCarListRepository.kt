package com.example.carfaxassignmenmt.repository

import com.example.carfaxassignmenmt.carlist.CarListMockData
import com.example.domain.models.ApiResult
import com.example.domain.models.CarItem
import com.example.domain.repositories.ICarListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCarListRepository : ICarListRepository {

	override fun getCarList(): Flow<ApiResult<List<CarItem>>> {
		return flow { emit(ApiResult.Success(CarListMockData.cars)) }
	}

	override fun getCarItem(id: String): Flow<ApiResult<CarItem>> {
		return flow {
			CarListMockData.cars.find { it.vin == id }?.let { emit(ApiResult.Success(it)) }
				?: emit(ApiResult.Error(NullPointerException("No vin found")))
		}
	}


}