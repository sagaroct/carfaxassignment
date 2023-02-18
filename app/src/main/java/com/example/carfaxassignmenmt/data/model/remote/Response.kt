package com.example.carfaxassignmenmt.data.model.remote

import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("listings")
	val listings: List<ListingsItem>

)