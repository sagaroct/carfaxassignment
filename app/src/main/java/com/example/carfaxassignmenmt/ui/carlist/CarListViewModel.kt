package com.example.carfaxassignmenmt.ui.carlist

import android.util.Log
import androidx.lifecycle.*
import com.example.carfaxassignmenmt.data.model.local.CarListItem
import com.example.carfaxassignmenmt.data.model.local.CarListResponse
import com.example.carfaxassignmenmt.data.network.ICarListRepository
import com.example.carfaxassignmenmt.data.repository.CarListRepository
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

    private val carListResponseMutableStateFlow: MutableStateFlow<CarListResponse> = MutableStateFlow(CarListResponse.Loading)
    /**
     * Expose the StateFlow CarListResponse so the UI can observe it.
     */
    val carListResponseStateFlow: StateFlow<CarListResponse>
        get() = carListResponseMutableStateFlow

     fun getCarListFromRepository() {
        viewModelScope.launch(Dispatchers.IO) {
            carListRepository.getCarList()
                .retryWhen { _, attempt -> attempt < 3 }
                .catch { error ->
                    Log.e(TAG, "getCarListFromRepository: ${error.message}")
                    carListResponseMutableStateFlow.value = CarListResponse.Error
                    emit(listOf())
                }.collect {
                    carListResponseMutableStateFlow.value = CarListResponse.Success(it)
                }
        }
    }

}