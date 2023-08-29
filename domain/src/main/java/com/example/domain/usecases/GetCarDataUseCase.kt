package com.example.domain.usecases

import com.example.domain.models.CarListItem
import com.example.domain.repositories.ICarListRepository
import kotlinx.coroutines.flow.Flow

/**
 * An Use case for getting car list and car item, where carListRepository is constructor injected.
 */
class GetCarDataUseCase(private val carListRepository: ICarListRepository) {

    fun getCarList(): Flow<List<CarListItem>> {
        return carListRepository.getCarList()
    }

    fun getCarItem(id: String): Flow<CarListItem> {
        return carListRepository.getCarItem(id)
    }
}
