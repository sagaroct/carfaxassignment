package com.example.domain.usecases

import com.example.domain.models.ApiResult
import com.example.domain.models.CarItem
import com.example.domain.repositories.ICarListRepository
import kotlinx.coroutines.flow.Flow

/**
 * An Use case for getting car item, where carListRepository is constructor injected.
 */
class GetCarItemUseCase(private val carListRepository: ICarListRepository) {

    operator fun invoke(id: String): Flow<ApiResult<CarItem>> {
        return carListRepository.getCarItem(id)
    }

}
