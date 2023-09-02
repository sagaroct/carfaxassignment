package com.example.data.carlist.model.remote

import com.google.gson.annotations.SerializedName

data class Dealer(

	@field:SerializedName("phone")
	val phone: String,

	@field:SerializedName("address")
	val address: String
)