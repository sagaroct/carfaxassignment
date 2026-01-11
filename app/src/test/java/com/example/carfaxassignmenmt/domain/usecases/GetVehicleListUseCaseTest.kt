package com.example.carfaxassignmenmt.domain.usecases

import com.example.carfaxassignmenmt.viewmodel.VehicleMockData
import com.example.carfaxassignmenmt.repository.FakeCarListRepository
import com.example.domain.models.ApiResult
import com.example.domain.usecases.GetVehicleListUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetVehicleListUseCaseTest{

	private lateinit var getVehicleListUseCase: GetVehicleListUseCase
	private lateinit var fakeCarListRepository: FakeCarListRepository

	@Before
	fun setUp() {
		fakeCarListRepository = FakeCarListRepository()
		getVehicleListUseCase = GetVehicleListUseCase(fakeCarListRepository)
	}

	@Test
	fun testGetVehicleListUseCase_Positive() = runTest {
		val apiResult = getVehicleListUseCase().first()
		assert(apiResult is ApiResult.Success)
		val apiSuccessResult = apiResult as ApiResult.Success
		assert(apiSuccessResult.data.size == 2)
		assert(apiSuccessResult.data == VehicleMockData.vehicles)
	}

	@Test
	fun testGetVehicleUseCase_Negative_wrongVin_returnsError() = runTest {
		fakeCarListRepository = FakeCarListRepository(isSuccess = false)
		getVehicleListUseCase = GetVehicleListUseCase(fakeCarListRepository)

		val apiResult = getVehicleListUseCase().first()

		assert(apiResult is ApiResult.Error)
		val error = (apiResult as ApiResult.Error).error
		assert(error is NullPointerException)
		assert(error.message == "Not Found")
	}


}