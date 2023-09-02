package com.example.domain.repositories

import com.example.domain.models.ApiResult
import com.example.domain.models.CarItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Sagar Pujari on 24/01/23.
 */
interface ICarListRepository {

    fun getCarList(): Flow<ApiResult<List<CarItem>>>

    fun getCarItem(id: String): Flow<ApiResult<CarItem>>

}