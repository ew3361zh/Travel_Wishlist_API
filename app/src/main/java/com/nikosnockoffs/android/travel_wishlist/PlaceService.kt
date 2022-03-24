package com.nikosnockoffs.android.travel_wishlist

import retrofit2.Response
import retrofit2.http.*

// Service is a general name for a class that makes requests to an API
interface PlaceService {

    @GET("places/")
    suspend fun getAllPlaces(): Response<List<Place>>

    // POST create place
    @POST("places/") // common to use same url in adding something and getting list of things - POST & GET
    suspend fun addPlace(@Body place: Place): Response<Place> // send data in body of request

    // update place - need ID of place and data abt place
    // sent in body of request
    @PATCH("places/{id}/")
    suspend fun updatePlace(@Body place: Place, @Path("id") id: Int): Response<Place>


    // delete place
    @DELETE("places/{id}/")
    suspend fun deletePlace(@Path("id") id: Int): Response<String>

}