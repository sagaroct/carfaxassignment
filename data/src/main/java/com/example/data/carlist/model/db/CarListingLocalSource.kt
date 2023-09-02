package com.example.data.carlist.model.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.common.Constants
import com.example.data.database.BaseDao
import kotlinx.coroutines.flow.Flow

/**
 * @author Sagar Pujari
 *
 * DAO implementation and local source for categories.
 */
@Dao
abstract class CarListingLocalSource : BaseDao<RoomCarListItem>() {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun create(item: RoomCarListItem): Long

    @Query("SELECT * FROM ${Constants.Table.ROOM_CAR_LIST_ITEM}")
    abstract fun get(): Flow<List<RoomCarListItem>>

    @Query("SELECT * FROM ${Constants.Table.ROOM_CAR_LIST_ITEM} WHERE vin = :id")
    abstract fun get(id: String):  Flow<RoomCarListItem>

    @Query("DELETE FROM ${Constants.Table.ROOM_CAR_LIST_ITEM}")
    abstract suspend fun deleteAll()

}