package com.example.carfaxassignmenmt.viewmodel

import com.example.carfaxassignmenmt.MainDispatcherRule
import com.example.carfaxassignmenmt.ui.vehiclelist.VehicleListViewModel
import com.example.domain.models.ApiResult
import com.example.domain.usecases.GetVehicleListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class VehicleListViewModelTest {

    @MockK
    lateinit var getVehicleListUseCase: GetVehicleListUseCase

	@ExperimentalCoroutinesApi
	private val testDispatcher = UnconfinedTestDispatcher()

	@OptIn(ExperimentalCoroutinesApi::class)
	@get:Rule
	val mainDispatcherRule = MainDispatcherRule(testDispatcher)

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetVehicleList_Positive() = runTest {
        coEvery { getVehicleListUseCase() } returns flow {
            emit(ApiResult.Success(VehicleMockData.vehicles))
        }
        val vehicleListViewModel = VehicleListViewModel(getVehicleListUseCase, testDispatcher)
	    val uiState = vehicleListViewModel.uiState.first()
	    coVerify(exactly = 1) { getVehicleListUseCase() }
	    assert(uiState.vehicles == VehicleMockData.vehicles)
	    assert(uiState.error == null)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetVehicleList_Negative() = runTest {
        val exception = NullPointerException("Empty car list")
        coEvery { getVehicleListUseCase() } returns flow {
            emit(ApiResult.Error(exception))
        }
        val vehicleListViewModel = VehicleListViewModel(getVehicleListUseCase, testDispatcher)
	    val uiState = vehicleListViewModel.uiState.first()
	    coVerify(exactly = 1) { getVehicleListUseCase() }
	    assert(uiState.vehicles.isEmpty())
	    assert(uiState.error == exception.message)

    }


@Test
fun testOnCallDealerClicked_updatesUiState() = runTest {
	coEvery { getVehicleListUseCase() } returns flow { emit(ApiResult.Success(emptyList())) }
	val phoneNumber = "123-456-7890"
    val vehicleListViewModel = VehicleListViewModel(getVehicleListUseCase, testDispatcher)
    vehicleListViewModel.onCallDealerClicked(phoneNumber)
    val uiState = vehicleListViewModel.uiState.first()
    assert(uiState.shouldShowCallDialog)
    assert(uiState.selectedPhoneNumber == phoneNumber)
}

@Test
fun testOnCallDialogDismissed_resetsUiState() = runTest {
	coEvery { getVehicleListUseCase() } returns flow { emit(ApiResult.Success(emptyList())) }
	val vehicleListViewModel = VehicleListViewModel(getVehicleListUseCase, testDispatcher)
    vehicleListViewModel.onCallDealerClicked("123-456-7890")
    vehicleListViewModel.onCallDialogDismissed()
    val uiState = vehicleListViewModel.uiState.first()
    assert(!uiState.shouldShowCallDialog)
    assert(uiState.selectedPhoneNumber == "")
}


}