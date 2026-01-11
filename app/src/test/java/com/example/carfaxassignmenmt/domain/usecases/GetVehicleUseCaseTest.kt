package com.example.carfaxassignmenmt.domain.usecases

import com.example.carfaxassignmenmt.viewmodel.VehicleMockData
import com.example.carfaxassignmenmt.repository.FakeCarListRepository
import com.example.domain.models.ApiResult
import com.example.domain.usecases.GetVehicleUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetVehicleUseCaseTest{

	private lateinit var getVehicleUseCase: GetVehicleUseCase
	private lateinit var fakeCarListRepository: FakeCarListRepository

	@Before
	fun setUp() {
		fakeCarListRepository = FakeCarListRepository()
		getVehicleUseCase = GetVehicleUseCase(fakeCarListRepository)
	}

	@Test
	fun testGetVehicleUseCase_Positive() = runTest {
		val vin = "111"
		val apiResult = getVehicleUseCase(vin).first()
		assert(apiResult is ApiResult.Success)
		val apiSuccessResult = apiResult as ApiResult.Success
		assert(apiSuccessResult.data.vin == vin)
		assert(apiSuccessResult.data == VehicleMockData.vehicles.find { it.vin ==  vin})
	}

	@Test
	fun testGetVehicleUseCase_Negative() = runTest {
		val incorrectVin = "000"
		val result = getVehicleUseCase(incorrectVin)
		val apiResult = result.first()
		assert(apiResult is ApiResult.Error)
		val apiErrorResult = apiResult as ApiResult.Error
		assert(apiErrorResult.error.message == "No vin found")
	}

}