package com.example.carfaxassignmenmt.domain.usecases

import com.example.carfaxassignmenmt.carlist.CarListMockData
import com.example.carfaxassignmenmt.repository.FakeCarListRepository
import com.example.domain.models.ApiResult
import com.example.domain.usecases.GetCarItemUseCase
import com.example.domain.usecases.GetCarListUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.lang.NullPointerException

class GetCarItemUseCaseTest{

	private lateinit var getCarItemUseCase: GetCarItemUseCase
	private lateinit var fakeCarListRepository: FakeCarListRepository

	@Before
	fun setUp() {
		fakeCarListRepository = FakeCarListRepository()
		getCarItemUseCase = GetCarItemUseCase(fakeCarListRepository)
	}

	@Test
	fun testGetCarItemUseCase_Positive() = runTest {
		val vin = "111"
		val apiResult = getCarItemUseCase(vin).first()
		assert(apiResult is ApiResult.Success)
		val apiSuccessResult = apiResult as ApiResult.Success
		assert(apiSuccessResult.data.vin == vin)
		assert(apiSuccessResult.data == CarListMockData.cars.find { it.vin ==  vin})
	}

	@Test
	fun testGetCarItemUseCase_Negative() = runTest {
		val incorrectVin = "000"
		val result = getCarItemUseCase(incorrectVin)
		val apiResult = result.first()
		assert(apiResult is ApiResult.Error)
		val apiErrorResult = apiResult as ApiResult.Error
		assert(apiErrorResult.error.message == "No vin found")
	}

}