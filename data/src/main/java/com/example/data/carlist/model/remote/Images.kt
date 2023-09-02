package com.example.data.carlist.model.remote

import com.example.data.carlist.model.remote.FirstPhoto
import com.google.gson.annotations.SerializedName

data class Images(

    @field:SerializedName("firstPhoto")
	val firstPhoto: FirstPhoto,

    )