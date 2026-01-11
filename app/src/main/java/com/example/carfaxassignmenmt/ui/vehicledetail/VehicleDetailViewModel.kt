package com.example.carfaxassignmenmt.ui.vehicledetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ApiResult
import com.example.domain.models.Vehicle
import com.example.domain.usecases.GetVehicleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleDetailViewModel @Inject constructor(
	private val getVehicleUseCase: GetVehicleUseCase,
	private val dispatcher: CoroutineDispatcher
) : ViewModel() {

	private val _vehicleApiResultFlow: MutableStateFlow<ApiResult<Vehicle>> =
		MutableStateFlow(ApiResult.Loading)
	val vehicleApiResultFlow = _vehicleApiResultFlow.asStateFlow()

	fun getVehicle(id: String) {
		viewModelScope.launch(dispatcher) {
			getVehicleUseCase(id).collect { result ->
				_vehicleApiResultFlow.value = result
			}
		}
	}
}
