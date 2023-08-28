package com.example.domain.repositories

import com.example.domain.models.CarListItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Sagar Pujari on 24/01/23.
 */
interface ICarListRepository {

    fun getCarList(): Flow<List<CarListItem>>

    fun getCarItem(id: String): Flow<CarListItem>

}