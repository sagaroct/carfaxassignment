package com.example.carfaxassignmenmt.data.network

import com.example.carfaxassignmenmt.data.model.local.CarListItem
import com.example.carfaxassignmenmt.data.model.remote.ListingsItem
import kotlinx.coroutines.flow.Flow

/**
 * Created by Sagar Pujari on 24/01/23.
 */
interface ICarListRepository {

    fun getCarList(): Flow<List<CarListItem>>

    fun getCarItem(id: String): Flow<CarListItem>

}