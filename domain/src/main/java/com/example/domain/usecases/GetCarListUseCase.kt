package com.example.domain.usecases

import com.example.domain.models.ApiResult
import com.example.domain.models.CarItem
import com.example.domain.repositories.ICarListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.retryWhen

/**
 * An Use case for getting car list, where carListRepository is constructor injected.
 */
class GetCarListUseCase(private val carListRepository: ICarListRepository) {

    operator fun invoke(): Flow<ApiResult<List<CarItem>>>{
        return carListRepository.getCarList()
    }

}

/**
 *  This approach is only for "useless" use cases for which unit test is not required for eg like getDataFromRepository,
 * not for when use cases are actually doing something more than just calling the repository.
 * In simple language, we are just returning the lambda function here which is implemented by repository
 * and there is no other business logic than that.
 */
//fun interface GetCarListUseCase :  () -> Flow<ApiResult<List<CarItem>>>
