package com.example.data.model.remote

import com.example.data.model.remote.ListingsItem
import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("listings")
	val listings: List<ListingsItem>

)