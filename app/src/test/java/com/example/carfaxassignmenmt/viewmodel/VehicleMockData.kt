package com.example.carfaxassignmenmt.viewmodel

import com.example.domain.models.Vehicle

object VehicleMockData {

    val vehicles = listOf(
        Vehicle(
            vin = "111", transmission = "automatic", mileage = 120000,
            image = "https://media.carfax.com/img/vhr/oneprice/icon-up-med.png",
            interiorColor = "black",
            drivetype = "awd",
            engine = "4 cyl",
            bodytype = "sedan",
            exteriorColor = "grey",
            currentPrice = 15000,
            phone = "1232333333",
            address = "91 US Highway",
            year = 2015,
            make = "Honda",
            model = "Civic",
            trim = "Ex",
            fuel = "gasoline"
        ),
        Vehicle(
            vin = "222", transmission = "automatic", mileage = 120000,
            image = "https://media.carfax.com/img/vhr/oneprice/icon-up-med.png",
            interiorColor = "black",
            drivetype = "awd",
            engine = "4 cyl",
            bodytype = "sedan",
            exteriorColor = "grey",
            currentPrice = 15000,
            phone = "1232333333",
            address = "91 US Highway",
            year = 2015,
            make = "Honda",
            model = "Civic",
            trim = "Ex",
            fuel = "gasoline"
        )
    )
}