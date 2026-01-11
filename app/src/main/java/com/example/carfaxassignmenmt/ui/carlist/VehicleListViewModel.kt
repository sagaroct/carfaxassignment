package com.example.carfaxassignmenmt.ui.carlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ApiResult
import com.example.domain.models.Vehicle
import com.example.domain.usecases.GetVehicleListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
	getVehicleListUseCase: GetVehicleListUseCase,
	dispatcher: CoroutineDispatcher
) : ViewModel() {

	val carListApiResultFlow: StateFlow<ApiResult<List<Vehicle>>> =
		getVehicleListUseCase()
			.flowOn(dispatcher)
			.stateIn(
				scope = viewModelScope,
				started = SharingStarted.WhileSubscribed(5_000),
				initialValue = ApiResult.Loading
			)

}