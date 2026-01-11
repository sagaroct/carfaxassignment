package com.example.data.database

import androidx.room.*
import com.example.data.feature.vehicle.model.db.VehicleDao
import com.example.data.feature.vehicle.model.db.VehicleEntity

/**
 * @author Sagar Pujari
 *
 * VehicleDatabase class which registers all the entities and DAO's
 */
@Database(
    entities = [VehicleEntity::class],
    version = 1,
    exportSchema = true
)
abstract class VehicleDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
}