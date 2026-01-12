package com.example.carfaxassignmenmt.ui.vehiclelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.models.ApiResult
import com.example.domain.models.Vehicle
import com.example.domain.usecases.GetVehicleListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleListViewModel @Inject constructor(
	private val getVehicleListUseCase: GetVehicleListUseCase,
	private val dispatcher: CoroutineDispatcher
) : ViewModel() {

	private val _uiState = MutableStateFlow(VehicleListUiState(isLoading = true))
	val uiState: StateFlow<VehicleListUiState> = _uiState.asStateFlow()

	init {
		getVehicles()
	}

	private fun getVehicles() {
		viewModelScope.launch(dispatcher) {
			getVehicleListUseCase()
				.collect { apiResult ->
					_uiState.update { currentState ->
						when (apiResult) {
							is ApiResult.Loading -> currentState.copy(isLoading = true)
							is ApiResult.Success -> currentState.copy(
								vehicles = apiResult.data,
								isLoading = false,
								error = null
							)

							is ApiResult.Error -> currentState.copy(
								error = apiResult.error.message,
								isLoading = false
							)
						}
					}
				}
		}
	}

	fun onCallDealerClicked(phoneNumber: String) {
		_uiState.update { it.copy(shouldShowCallDialog = true, selectedPhoneNumber = phoneNumber) }
	}

	fun onCallDialogDismissed() {
		_uiState.update { it.copy(shouldShowCallDialog = false, selectedPhoneNumber = "") }
	}

}

data class VehicleListUiState(
	val vehicles: List<Vehicle> = emptyList(),
	val isLoading: Boolean = false,
	val error: String? = null,
	val shouldShowCallDialog: Boolean = false,
	val selectedPhoneNumber: String = ""
)