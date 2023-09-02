package com.example.domain.usecases

import com.example.domain.models.ApiResult
import com.example.domain.models.CarItem
import com.example.domain.repositories.ICarListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen

/**
 * An Use case for getting car item, where carListRepository is constructor injected.
 */
class GetCarListUseCase(private val carListRepository: ICarListRepository) {

    operator fun invoke(): Flow<ApiResult<List<CarItem>>>{
        return carListRepository.getCarList()
    }

}
