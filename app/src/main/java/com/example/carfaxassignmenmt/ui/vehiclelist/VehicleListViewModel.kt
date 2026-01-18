package com.example.carfaxassignmenmt.ui.vehiclelist

import android.util.Log.i
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ApiResult
import com.example.domain.models.Vehicle
import com.example.domain.usecases.GetVehicleListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
	private val getVehicleListUseCase: GetVehicleListUseCase,
	private val dispatcher: CoroutineDispatcher
) : ViewModel() {

	private val _uiState = MutableStateFlow<VehicleListUiState>(VehicleListUiState.Loading)
	val uiState: StateFlow<VehicleListUiState> = _uiState.asStateFlow()

	private val _sideEffect = MutableSharedFlow<VehicleListSideEffect>()
	val sideEffect = _sideEffect.asSharedFlow()

	init {
		getVehicles()
	}

	private fun getVehicles() {
		viewModelScope.launch(dispatcher) {
			getVehicleListUseCase()
				.collect { apiResult ->
					when (apiResult) {
						is ApiResult.Loading -> _uiState.update { VehicleListUiState.Loading }
						is ApiResult.Success -> _uiState.update {
							VehicleListUiState.Shown(
								vehicles = apiResult.data,
							)
						}

						is ApiResult.Error -> {
							sendSideEffect(
								VehicleListSideEffect.ShowErrorMessage(
									apiResult.error.message ?: "Unknown error"
								)
							)
							// Update UI state to show error for cases when data is not cached and UI needs to reflect error state.
							_uiState.update {
								VehicleListUiState.Error(
									message = apiResult.error.message
										?: "An unexpected error occurred"
								)
							}
						}
					}
				}
		}
	}

	private fun sendSideEffect(effect: VehicleListSideEffect) {
		viewModelScope.launch {
			_sideEffect.emit(effect)
		}
	}

	fun sendUiEvent(event: VehicleListUiEvent) {
		if(_uiState.value !is VehicleListUiState.Shown) return
		when (event) {
			is VehicleListUiEvent.CallDealerCTA -> {
				_uiState.update { current ->
					val shown = current as VehicleListUiState.Shown
					shown.copy(
						shouldShowCallDialog = true,
						selectedPhoneNumber = event.phoneNumber
					)
				}
			}

			is VehicleListUiEvent.CallDialogDismissed -> {
				_uiState.update { current ->
					val shown = current as VehicleListUiState.Shown
					shown.copy(shouldShowCallDialog = false, selectedPhoneNumber = "")
					}
			}
		}
	}
}

sealed interface VehicleListUiState {
	object Loading : VehicleListUiState
	data class Shown(
		val vehicles: List<Vehicle>, val shouldShowCallDialog: Boolean = false,
		val selectedPhoneNumber: String = ""
	) : VehicleListUiState
	data class Error(val message: String) : VehicleListUiState
}


sealed interface VehicleListUiEvent {
	data class CallDealerCTA(val phoneNumber: String) : VehicleListUiEvent
	data object CallDialogDismissed : VehicleListUiEvent
}

sealed interface VehicleListSideEffect {
	data class ShowErrorMessage(val message: String) : VehicleListSideEffect
}