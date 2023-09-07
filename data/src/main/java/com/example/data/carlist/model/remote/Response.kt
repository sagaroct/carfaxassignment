package com.example.data.carlist.model.remote

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("listings")
	val listings: List<RemoteCarItem>

)