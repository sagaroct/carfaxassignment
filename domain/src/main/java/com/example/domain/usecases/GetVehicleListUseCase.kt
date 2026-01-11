package com.example.domain.usecases

import com.example.domain.models.ApiResult
import com.example.domain.models.Vehicle
import com.example.domain.repositories.IVehicleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * An Use case for getting car item, where carListRepository is constructor injected.
 */
class GetVehicleListUseCase @Inject constructor(private val carListRepository: IVehicleRepository) {

    operator fun invoke(): Flow<ApiResult<List<Vehicle>>>{
        return carListRepository.getVehicleList()
    }

}
