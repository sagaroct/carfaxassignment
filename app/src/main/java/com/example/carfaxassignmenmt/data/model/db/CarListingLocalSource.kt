package com.example.carfaxassignmenmt.data.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.carfaxassignmenmt.common.Constants
import com.example.carfaxassignmenmt.data.database.BaseDao

/**
 * @author Sagar Pujari
 *
 * DAO implementation and local source for categories.
 */
@Dao
abstract class CarListingLocalSource : BaseDao<RoomCarListItem>() {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun create(item: RoomCarListItem): Long

    @Query("SELECT * FROM ${Constants.Table.ROOM_CAR_LIST_ITEM}")
    abstract suspend fun get(): List<RoomCarListItem>

    @Query("SELECT * FROM ${Constants.Table.ROOM_CAR_LIST_ITEM} WHERE vin = :id")
    abstract suspend fun get(id: String): RoomCarListItem

    @Query("DELETE FROM ${Constants.Table.ROOM_CAR_LIST_ITEM}")
    abstract suspend fun deleteAll()

}