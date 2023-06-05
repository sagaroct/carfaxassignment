package com.example.data.model.remote

import com.example.data.model.remote.FirstPhoto
import com.google.gson.annotations.SerializedName

data class Images(

	@field:SerializedName("firstPhoto")
	val firstPhoto: FirstPhoto,

	)