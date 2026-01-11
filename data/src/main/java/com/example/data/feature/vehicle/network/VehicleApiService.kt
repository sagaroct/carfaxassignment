package com.example.data.feature.vehicle.network

import com.example.data.feature.vehicle.model.remote.Response
import retrofit2.http.GET

/**
 * Created by sagar on 20/8/16.
 */
interface VehicleApiService {

    @GET("assignment.json")
    suspend fun getVehicles(): Response

}