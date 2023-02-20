package com.example.carfaxassignmenmt.data.model.local

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by Sagar Pujari on 18/02/23.
 */
@Parcelize
data class CarListItem (val vin: String,
                       val transmission: String,
                       val mileage: Int,
                       val image: String,
                       val interiorColor: String,
                       val drivetype: String,
                       val engine: String,
                       val bodytype: String,
                       val exteriorColor: String,
                       val currentPrice: Int,
                       val phone: String,
                       val address: String,
                       val year: Int,
                       val make: String,
                       val model: String,
                       val trim: String,
                        val fuel: String) : Parcelable
