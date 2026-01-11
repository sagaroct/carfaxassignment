package com.example.carfaxassignmenmt.viewmodel

import com.example.carfaxassignmenmt.MainDispatcherRule
import com.example.carfaxassignmenmt.ui.carlist.VehicleListViewModel
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
/*
    @InjectMockKs
    private lateinit var vehicleListViewModel: VehicleListViewModel*/

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
	    val apiResult = vehicleListViewModel.carListApiResultFlow.first()
	    coVerify(exactly = 1) { getVehicleListUseCase() }
	    assert(apiResult is ApiResult.Success)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetVehicleList_Negative() = runTest {
	    coEvery { getVehicleListUseCase() } returns flow {
            emit(ApiResult.Error(NullPointerException("Empty car list")))
        }
	    val vehicleListViewModel = VehicleListViewModel(getVehicleListUseCase, testDispatcher)

	    val apiResult = vehicleListViewModel.carListApiResultFlow.first()

	    coVerify(exactly = 1) { getVehicleListUseCase() }
	    assert(apiResult is ApiResult.Error)
	    val apiErrorResult = apiResult as ApiResult.Error
	    assert(apiErrorResult.error == apiResult.error)
    }



}