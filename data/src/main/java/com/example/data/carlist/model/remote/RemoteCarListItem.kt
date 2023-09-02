package com.example.data.carlist.model.remote

import com.example.data.carlist.model.remote.Dealer
import com.example.data.carlist.model.remote.Images
import com.google.gson.annotations.SerializedName

data class RemoteCarListItem(

	@field:SerializedName("transmission")
	val transmission: String,

	@field:SerializedName("vin")
	val vin: String,

	@field:SerializedName("mileage")
	val mileage: Int,

	@field:SerializedName("images")
	val images: Images,

	@field:SerializedName("interiorColor")
	val interiorColor: String,

	@field:SerializedName("drivetype")
	val drivetype: String,

	@field:SerializedName("engine")
	val engine: String,

	@field:SerializedName("bodytype")
	val bodytype: String,

	@field:SerializedName("exteriorColor")
	val exteriorColor: String,

	@field:SerializedName("currentPrice")
	val currentPrice: Int,

	@field:SerializedName("dealer")
	val dealer: Dealer,

	@field:SerializedName("year")
	val year: Int,

	@field:SerializedName("make")
	val make: String,

	@field:SerializedName("model")
	val model: String,

	@field:SerializedName("trim")
	val trim: String,

	@field:SerializedName("fuel")
	val fuel: String

	)