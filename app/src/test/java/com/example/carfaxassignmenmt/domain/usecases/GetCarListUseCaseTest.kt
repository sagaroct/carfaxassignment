package com.example.carfaxassignmenmt.domain.usecases

import com.example.carfaxassignmenmt.carlist.CarListMockData
import com.example.carfaxassignmenmt.repository.FakeCarListRepository
import com.example.domain.models.ApiResult
import com.example.domain.usecases.GetCarListUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetCarListUseCaseTest{

	private lateinit var getCarListUseCase: GetCarListUseCase
	private lateinit var fakeCarListRepository: FakeCarListRepository

	@Before
	fun setUp() {
		fakeCarListRepository = FakeCarListRepository()
		getCarListUseCase = GetCarListUseCase(fakeCarListRepository)
	}

	@Test
	fun testGetCarListUseCase_Positive() = runTest {
		val apiResult = getCarListUseCase().first()
		assert(apiResult is ApiResult.Success)
		val apiSuccessResult = apiResult as ApiResult.Success
		assert(apiSuccessResult.data.size == 2)
		assert(apiSuccessResult.data == CarListMockData.cars)
	}

}