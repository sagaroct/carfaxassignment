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
		val uiState = vehicleDetailViewModel.uiState.value
		verify(exactly = 1) { getVehicleUseCase(vin) }
		assert(uiState.vehicle?.vin == vin)
		assert(uiState.error == null)
	}

	@Test
	fun testGetVehicle_Negative() = runTest {
		val vin = "null"
		coEvery { getVehicleUseCase(vin) } returns flow {
			emit(ApiResult.Error(NullPointerException("Not found")))
		}
		vehicleDetailViewModel.getVehicle(vin)
		val uiState = vehicleDetailViewModel.uiState.value
		coVerify(exactly = 1) { getVehicleUseCase(vin) }
		assert(uiState.vehicle == null)
		assert(uiState.error == "Not found")
	}

	// src/test/java/com/example/carfaxassignmenmt/viewmodel/VehicleDetailViewModelTest.kt

	@Test
	fun testOnCallDealerClicked_updatesUiState() = runTest {
	    val phoneNumber = "123-456-7890"
	    vehicleDetailViewModel.onCallDealerClicked(phoneNumber)
	    val uiState = vehicleDetailViewModel.uiState.value
	    assert(uiState.shouldShowCallDialog)
	    assert(uiState.selectedPhoneNumber == phoneNumber)
	}

	@Test
	fun testOnCallDialogDismissed_resetsUiState() = runTest {
	    // First, show the dialog
	    vehicleDetailViewModel.onCallDealerClicked("123-456-7890")
	    // Then, dismiss it
	    vehicleDetailViewModel.onCallDialogDismissed()
	    val uiState = vehicleDetailViewModel.uiState.value
	    assert(!uiState.shouldShowCallDialog)
	    assert(uiState.selectedPhoneNumber == "")
	}

}