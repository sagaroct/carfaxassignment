package com.example.data.feature.vehicle.model.db

import androidx.room.Dao
import androidx.room.Query
import com.example.data.common.Constants
import com.example.data.database.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * @author Sagar Pujari
 * DAO interface for VehicleEntity to define database operations
 */
@Dao
interface VehicleDao : BaseDao<VehicleEntity> {

    @Query("SELECT * FROM ${Constants.Table.VEHICLES}")
    fun get(): Flow<List<VehicleEntity>>

    @Query("SELECT * FROM ${Constants.Table.VEHICLES} WHERE vin = :vin")
    fun get(vin: String):  Flow<VehicleEntity>

    @Query("DELETE FROM ${Constants.Table.VEHICLES}")
    suspend fun deleteAll()

}