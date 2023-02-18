package com.example.carfaxassignmenmt.data.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.carfaxassignmenmt.common.Constants

/**
 * @author Sagar Pujari
 * Room entity class and a table blueprint for CarListItem,
 */
@Entity(tableName = Constants.Table.ROOM_CAR_LIST_ITEM)
data class RoomCarListItem(
    /*@PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,*/

    @PrimaryKey
    @ColumnInfo(name = "vin")
    val vin: String,

    @ColumnInfo(name = "transmission")
    val transmission: String,

    @ColumnInfo(name = "mileage")
    val mileage: Int,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "interiorColor")
    val interiorColor: String,

    @ColumnInfo(name = "drivetype")
    val drivetype: String,

    @ColumnInfo(name = "engine")
    val engine: String,

    @ColumnInfo(name = "bodytype")
    val bodytype: String,

    @ColumnInfo(name = "exteriorColor")
    val exteriorColor: String,

    @ColumnInfo(name = "currentPrice")
    val currentPrice: Int,

    @ColumnInfo(name = "phone")
    val phone: String,

    @ColumnInfo(name = "address")
    val address: String,

    @ColumnInfo(name = "year")
    val year: Int,

    @ColumnInfo(name = "make")
    val make: String,

    @ColumnInfo(name = "model")
    val model: String,

    @ColumnInfo(name = "trim")
    val trim: String,
)

