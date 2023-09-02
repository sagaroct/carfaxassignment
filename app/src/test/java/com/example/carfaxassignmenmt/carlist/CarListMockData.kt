package com.example.carfaxassignmenmt.carlist

import com.example.domain.models.CarItem

object CarListMockData {

    val cars = listOf(
        CarItem(
            vin = "11111111", transmission = "automatic", mileage = 120000,
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
        CarItem(
            vin = "11111111", transmission = "automatic", mileage = 120000,
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