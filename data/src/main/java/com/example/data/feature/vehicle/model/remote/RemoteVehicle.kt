package com.example.data.feature.vehicle.model.remote

data class RemoteVehicle(

	val transmission: String,

	val vin: String,

	val mileage: Int,

	val images: Images,

	val interiorColor: String,

	val drivetype: String,

	val engine: String,

	val bodytype: String,

	val exteriorColor: String,

	val currentPrice: Int,

	val dealer: Dealer,

	val year: Int,

	val make: String,

	val model: String,

	val trim: String,

	val fuel: String

	)