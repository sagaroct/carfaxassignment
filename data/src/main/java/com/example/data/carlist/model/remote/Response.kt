package com.example.data.carlist.model.remote

import com.example.data.carlist.model.remote.RemoteCarListItem
import com.google.gson.annotations.SerializedName

data class Response(

	@field:SerializedName("listings")
	val listings: List<RemoteCarListItem>

)