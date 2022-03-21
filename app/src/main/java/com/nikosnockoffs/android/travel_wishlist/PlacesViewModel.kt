package com.nikosnockoffs.android.travel_wishlist

import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.*

const val TAG = "PLACES_VIEW_MODEL"
class PlacesViewModel: ViewModel() {

    // making list private to make sure ViewModel is controlling access - e.g. to prevent duplicates
    private val places = mutableListOf<Place>(Place("Berlin, Germany", "The graffiti art scene", starred = true),
        Place("Prague, Hungary", "The architecture", starred = true), Place("Amsterdam, Netherlands", "Art and music and food", starred = false))

    // Kotlin is doing a "smart-cast" to convert mutable list to immutable list
    fun getPlaces(){
        // todo
    }

    fun addNewPlace(place: Place) {
        // todo
    }


    fun deletePlace(place: Place) {
        // todo
    }

    fun updatePlace(place: Place) {
        // todo
    }
}