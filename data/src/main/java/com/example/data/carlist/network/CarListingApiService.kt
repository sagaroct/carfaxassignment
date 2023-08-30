
package com.example.data.carlist.network

import com.example.data.model.remote.Response
import retrofit2.http.GET

/**
 * Created by sagar on 20/8/16.
 */
interface CarListingApiService {

    @GET("assignment.json")
    suspend fun getCarListings(): Response

}