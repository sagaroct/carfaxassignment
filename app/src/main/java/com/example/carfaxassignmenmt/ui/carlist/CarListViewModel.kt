package com.example.carfaxassignmenmt.ui.carlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ApiResult
import com.example.domain.models.CarItem
import com.example.domain.usecases.GetCarItemUseCase
import com.example.domain.usecases.GetCarListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarListViewModel @Inject constructor(
    private val getCarListUseCase: GetCarListUseCase,
    private val getCarItemUseCase: GetCarItemUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    companion object {
        private const val TAG = "CarListViewModel"
    }

    private val carListMutableApiResultFlow: MutableStateFlow<ApiResult<List<CarItem>>> =
        MutableStateFlow(
            ApiResult.Loading
        )
    val carListApiResultFlow: StateFlow<ApiResult<List<CarItem>>>
        get() = carListMutableApiResultFlow

    private val carItemMutableApiResultFlow: MutableStateFlow<ApiResult<CarItem>> =
        MutableStateFlow(
            ApiResult.Loading
        )
    val carItemApiResultFlow: StateFlow<ApiResult<CarItem>>
        get() = carItemMutableApiResultFlow

    fun getCarListFromRepository() {
        viewModelScope.launch(dispatcher) {
            getCarListUseCase().collect {
                carListMutableApiResultFlow.value = it
            }
        }
    }

    fun getCarItemFromRepository(id: String) {
        viewModelScope.launch(dispatcher) {
            getCarItemUseCase(id).collect {
                carItemMutableApiResultFlow.value = it
            }
        }
    }

}