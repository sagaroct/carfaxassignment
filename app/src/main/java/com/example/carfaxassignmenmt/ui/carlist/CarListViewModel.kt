package com.example.carfaxassignmenmt.ui.carlist

import android.util.Log
import androidx.lifecycle.*
import com.example.carfaxassignmenmt.data.model.local.ApiResult
import com.example.carfaxassignmenmt.data.model.local.CarListItem
import com.example.carfaxassignmenmt.data.network.ICarListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarListViewModel @Inject constructor(private val carListRepository: ICarListRepository) : ViewModel()  {

    companion object {
        private const val TAG = "CarListViewModel"
    }

    private val carListMutableApiResultFlow: MutableStateFlow<ApiResult<List<CarListItem>>> = MutableStateFlow(ApiResult.Loading)
    val carListApiResultFlow: StateFlow<ApiResult<List<CarListItem>>>
        get() = carListMutableApiResultFlow

    private val carItemMutableApiResultFlow: MutableStateFlow<ApiResult<CarListItem>> = MutableStateFlow(ApiResult.Loading)
    val carItemApiResultFlow: StateFlow<ApiResult<CarListItem>>
        get() = carItemMutableApiResultFlow

     fun getCarListFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            carListRepository.getCarList()
                .retryWhen { _, attempt -> attempt < 3 }
                .catch { error ->
                    Log.e(TAG, "getCarListFromRepository: ${error.message}")
                    carListMutableApiResultFlow.value = ApiResult.Error
                    emit(listOf())
                }.collect {
                    carListMutableApiResultFlow.value = ApiResult.Success(it)
                }
        }
    }

    fun getCarItemFromRepository(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            carListRepository.getCarItem(id)
                .retryWhen { _, attempt -> attempt < 3 }
                .catch { error ->
                    Log.e(TAG, "getCarListFromRepository: ${error.message}")
                    carItemMutableApiResultFlow.value = ApiResult.Error
                }.collect {
                    carItemMutableApiResultFlow.value = ApiResult.Success(it)
                }
        }
    }

}