package com.nikosnockoffs.android.travel_wishlist

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PlaceRepository {

    private val TAG = "PLACE_REPOSITORY"

    private val baseURL = "https://place-wish-list.herokuapp.com/api/"

    private val client: OkHttpClient = OkHttpClient.Builder() // does work of making request to API server
        .addInterceptor(AuthorizationHeaderInterceptor())
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(baseURL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create()) // convert json object into json/kotlin
        .build() // create retrofit object to use in code

    private val placeService = retrofit.create(PlaceService::class.java)

    suspend fun getAllPlaces(): ApiResult<List<Place>> {
        try {
            val response = placeService.getAllPlaces()

            if (response.isSuccessful) { // connected, got data back
                val places = response.body()
                Log.d(TAG, "List of places {$places}")
                return ApiResult(ApiStatus.SUCCESS, response.body(), null)
            } else { // connected to server but server sent an error message
                Log.e(TAG, "Error fetching places from API server ${response.errorBody()}")
                return ApiResult(ApiStatus.SERVER_ERROR, null, "Error fetching places")
            }

        } catch (ex: Exception) { // can't connect to the server - network error eg
            Log.e(TAG, "Error connecting to API server", ex)
            return ApiResult(ApiStatus.SERVER_ERROR, null, "Can't connect to server")
        }
    }

    suspend fun addPlace(place: Place): ApiResult<Place> {
        try {
            val response = placeService.addPlace(place)
            if (response.isSuccessful) {
                Log.d(TAG, "Success creating new place $place")
                Log.d(TAG, "Server created new place ${response.body()}")
                return ApiResult(ApiStatus.SUCCESS, null, "Place created!")
            }
            Log.e(TAG, "Error creating new place ${response.errorBody()}")
            return ApiResult(ApiStatus.SERVER_ERROR, null, "Error adding a place - is name unique?")
        } catch (ex: Exception) { // can't connect to the server - network error eg
            Log.e(TAG, "Error connecting to API server", ex)
            return ApiResult(ApiStatus.SERVER_ERROR, null, "Can't connect to server")
        }

    }

    suspend fun updatePlace(place: Place): ApiResult<Place> {

        // todo avoid writing same error handling code multiple times
        // todo report errors/success to user
        //  - 3 outcomes - success (maybe data), connect but server error, can't connect network error

        if (place.id == null) {
            Log.e(TAG, "Error trying to update place")
            return ApiResult(ApiStatus.SERVER_ERROR, null, "Error updating place")
        } else {

            try {
                val response = placeService.updatePlace(place, place.id)
                if (response.isSuccessful) {
                    Log.d(TAG, "Update place successful!")
                    return ApiResult(ApiStatus.SUCCESS, null, "Place updated!")
                } else {
                    Log.e(TAG, "Error updating place ${response.errorBody()}")
                    return ApiResult(ApiStatus.SERVER_ERROR, null, "Error updating place")
                }
            } catch (ex: Exception) { // can't connect to the server - network error eg
                Log.e(TAG, "Error connecting to API server", ex)
                return ApiResult(ApiStatus.SERVER_ERROR, null, "Can't connect to server")
            }

        }

    }

    suspend fun deletePlace(place: Place): ApiResult<Nothing> {

        if (place.id == null) {
            Log.e(TAG, "Error trying to delete place")
            return ApiResult(ApiStatus.SERVER_ERROR, null, "Error deleting place")
        } else {
            try {
                val response = placeService.deletePlace(place.id)
                if (response.isSuccessful) {
                    Log.d(TAG, "Delete place successful!")
                    return ApiResult(ApiStatus.SUCCESS, null, "Place deleted!")
                } else {
                    Log.e(TAG, "Error deleting place ${response.errorBody()}")
                    return ApiResult(ApiStatus.SERVER_ERROR, null, "Error deleting place")
                }
            } catch (ex: Exception) { // can't connect to the server - network error eg
                Log.e(TAG, "Error connecting to API server", ex)
                return ApiResult(ApiStatus.SERVER_ERROR, null, "Can't connect to server")
            }

        }

    }

}
