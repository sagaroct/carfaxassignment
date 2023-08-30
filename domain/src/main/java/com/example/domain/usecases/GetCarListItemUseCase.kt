package com.example.domain.usecases

import com.example.domain.models.CarListItem
import com.example.domain.repositories.ICarListRepository
import kotlinx.coroutines.flow.Flow

/**
 * An Use case for getting car item, where carListRepository is constructor injected.
 */
class GetCarListItemUseCase(private val carListRepository: ICarListRepository) {

    operator fun invoke(): Flow<List<CarListItem>> {
        return carListRepository.getCarList()
    }

}
