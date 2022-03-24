package com.nikosnockoffs.android.travel_wishlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

const val TAG = "PLACES_VIEW_MODEL"
class PlacesViewModel: ViewModel() {

    // making list private to make sure ViewModel is controlling access - e.g. to prevent duplicates
//    private val places = mutableListOf<Place>(Place("Berlin, Germany", "The graffiti art scene", starred = true),
//        Place("Prague, Hungary", "The architecture", starred = true), Place("Amsterdam, Netherlands", "Art and music and food", starred = false))

    private val placeRepository = PlaceRepository()

    val allPlaces = MutableLiveData<List<Place>>(listOf<Place>())
    val userMessage = MutableLiveData<String>(null)

    init {
        getPlaces()
    }

    // Kotlin is doing a "smart-cast" to convert mutable list to immutable list
    fun getPlaces(){
        viewModelScope.launch {
            val apiResult = placeRepository.getAllPlaces()
            if (apiResult.status == ApiStatus.SUCCESS) {
                allPlaces.postValue(apiResult.data)
            } else {
                userMessage.postValue(apiResult.message)
            }

        }
    }

    fun addNewPlace(place: Place) {
        viewModelScope.launch {
            val apiResult = placeRepository.addPlace(place)
            updateUI(apiResult)
        }
    }

    fun updatePlace(place: Place) {
        viewModelScope.launch {
            val apiResult = placeRepository.updatePlace(place)
            updateUI(apiResult)
        }
    }

    fun deletePlace(place: Place) {
        viewModelScope.launch {
            val apiResult = placeRepository.deletePlace(place)
            updateUI(apiResult)
        }
    }

    private fun updateUI(result: ApiResult<Any>) {
        if (result.status == ApiStatus.SUCCESS) {
            getPlaces()

        }
        userMessage.postValue(result.message)
    }

}