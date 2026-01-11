package com.example.carfaxassignmenmt.viewmodel

import com.example.carfaxassignmenmt.MainDispatcherRule
import com.example.carfaxassignmenmt.ui.vehicledetail.VehicleDetailViewModel
import com.example.domain.models.ApiResult
import com.example.domain.usecases.GetVehicleUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VehicleDetailViewModelTest {

	@MockK
	lateinit var getVehicleUseCase: GetVehicleUseCase

	@ExperimentalCoroutinesApi
	private val testDispatcher = UnconfinedTestDispatcher()

	@OptIn(ExperimentalCoroutinesApi::class)
	@get:Rule
	val mainDispatcherRule = MainDispatcherRule(testDispatcher)

	@InjectMockKs
	private lateinit var vehicleDetailViewModel: VehicleDetailViewModel

	@Before
	fun setUp() {
		MockKAnnotations.init(this)
	}

	@Test
	fun testGetVehicle_Positive() = runTest {
		val vin = VehicleMockData.vehicles[0].vin
		every { getVehicleUseCase(vin) } returns flow {
			emit(ApiResult.Success(VehicleMockData.vehicles[0]))
		}
		vehicleDetailViewModel.getVehicle(vin)
		val apiResult = vehicleDetailViewModel.vehicleApiResultFlow.value
		verify(exactly = 1) { getVehicleUseCase(vin) }
		assert(apiResult is ApiResult.Success)
		val apiSuccessResult = apiResult as ApiResult.Success
		assert(apiSuccessResult.data.vin == vin)
	}

	@Test
	fun testGetVehicle_Negative() = runTest {
		val vin = "null"
		coEvery { getVehicleUseCase(vin) } returns flow {
			emit(ApiResult.Error(NullPointerException("Not found")))
		}
		vehicleDetailViewModel.getVehicle(vin)
		val apiResult = vehicleDetailViewModel.vehicleApiResultFlow.value
		coVerify(exactly = 1) { getVehicleUseCase(vin) }
		assert(apiResult is ApiResult.Error)
		val apiErrorResult = apiResult as ApiResult.Error
		assert(apiErrorResult.error == apiResult.error)
	}

}