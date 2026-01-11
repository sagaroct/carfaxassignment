package com.example.data.feature.vehicle.model.remote

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("listings")
	val listings: List<RemoteVehicle>

)