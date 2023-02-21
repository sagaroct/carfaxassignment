package com.example.carfaxassignmenmt.common

import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

/**
 * Created by Sagar Pujari on 21/02/23.
 */
object UnitConverter {

    fun priceWithComma(price: Int): String {
        val formatter = NumberFormat.getInstance() as DecimalFormat
        formatter.applyPattern("#,###")
        return formatter.format(price)
    }

    fun numberWithK(num: Int): String {
        if (num >= 1000) {
            return "${roundOffDecimal(num / 1000.0)}k"
        }
        return "${num}k"
    }

    private fun roundOffDecimal(number: Double): Double {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number).toDouble()
    }

}