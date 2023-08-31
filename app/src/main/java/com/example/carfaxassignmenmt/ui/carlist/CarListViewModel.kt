package com.example.carfaxassignmenmt.ui.carlist

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.ApiResult
import com.example.domain.models.CarListItem
import com.example.domain.usecases.GetCarItemUseCase
import com.example.domain.usecases.GetCarListItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CarListViewModel @Inject constructor(private val getCarItemUseCase: GetCarItemUseCase,
                                           private val getCarListItemUseCase: GetCarListItemUseCase) : ViewModel()  {

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
            getCarListItemUseCase().retryWhen { _, attempt -> attempt < 3 }
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
            getCarItemUseCase(id).retryWhen { _, attempt -> attempt < 3 }
                .catch { error ->
                    Log.e(TAG, "getCarListFromRepository: ${error.message}")
                    carItemMutableApiResultFlow.value = ApiResult.Error
                }.collect {
                    carItemMutableApiResultFlow.value = ApiResult.Success(it)
                }
        }
    }

}