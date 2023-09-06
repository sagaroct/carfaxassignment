package com.example.carfaxassignmenmt.carlist

import com.example.carfaxassignmenmt.ui.carlist.CarListViewModel
import com.example.domain.models.ApiResult
import com.example.domain.usecases.GetCarItemUseCase
import com.example.domain.usecases.GetCarListUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class CarListViewModelTest {

    @MockK
    lateinit var getCarListUseCase: GetCarListUseCase

    @MockK
    lateinit var getCarItemUseCase: GetCarItemUseCase

    @InjectMockKs
    private lateinit var carListViewModel: CarListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun testGetCarListFromRepository_Positive() = runTest {
        every { getCarListUseCase() } returns flow {
            emit(ApiResult.Success(CarListMockData.cars))
        }
        carListViewModel.getCarListFromRepository()
        val apiResult = carListViewModel.carListApiResultFlow.value
        verify(exactly = 1) { getCarListUseCase() }
        assert(apiResult is ApiResult.Success)
    }

    @Test
    fun testGetCarListFromRepository_Negative() = runTest {
        every { getCarListUseCase() } returns flow {
            emit(ApiResult.Error(NullPointerException("Empty car list")))
        }
        carListViewModel.getCarListFromRepository()
        val apiResult = carListViewModel.carListApiResultFlow.value
        verify(exactly = 1) { getCarListUseCase() }
        assert(apiResult is ApiResult.Error)
        val apiErrorResult = apiResult as ApiResult.Error
        assert(apiErrorResult.error == apiResult.error)
    }

    @Test
    fun testGetCarItemFromRepository_Positive() = runTest {
        val vin = CarListMockData.cars[0].vin
        every { getCarItemUseCase(vin) } returns flow {
            emit(ApiResult.Success(CarListMockData.cars[0]))
        }
        carListViewModel.getCarItemFromRepository(vin)
        val apiResult = carListViewModel.carItemApiResultFlow.value
        verify(exactly = 1) { getCarItemUseCase(vin) }
        assert(apiResult is ApiResult.Success)
        val apiSuccessResult = apiResult as ApiResult.Success
        assert(apiSuccessResult.data.vin == vin)
    }

    @Test
    fun testGetCarItemFromRepository_Negative() = runTest {
        val vin = "null"
        coEvery { getCarItemUseCase(vin) } returns flow {
            emit(ApiResult.Error(NullPointerException("Not found")))
        }
        carListViewModel.getCarItemFromRepository(vin)
        val apiResult = carListViewModel.carItemApiResultFlow.value
        coVerify(exactly = 1) { getCarItemUseCase(vin) }
        assert(apiResult is ApiResult.Error)
        val apiErrorResult = apiResult as ApiResult.Error
        assert(apiErrorResult.error == apiResult.error)
    }

}