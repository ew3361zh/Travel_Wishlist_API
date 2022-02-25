package com.nikosnockoffs.android.travel_wishlist

import android.util.Log
import androidx.lifecycle.ViewModel
import java.util.*

const val TAG = "PLACES_VIEW_MODEL"
class PlacesViewModel: ViewModel() {

    // making list private to make sure ViewModel is controlling access - e.g. to prevent duplicates
    private val places = mutableListOf<Place>(Place("Berlin, Germany"),
        Place("Prague, Hungary"), Place("Amsterdam, Netherlands"))

    // Kotlin is doing a "smart-cast" to convert mutable list to immutable list
    fun getPlaces(): List<Place> {
        return places
    }

    fun addNewPlace(place: Place, position: Int? = null): Int { // if we don't pass it a position,
                                                            // put it at the end, else, put in position
//        for (placeName in placeNames) {
//            if (placeName.uppercase() == place.uppercase()) {
//                return -1 // -1 means place was not added. an invalid position
//            }
//        }

        // another function called "all" that returns true if all the things in the list meet a condition
        // any function returns true if any of the things in a list meet a condition
        // avoid dupes
        if (places.any { placeName -> placeName.name.uppercase() == place.name.uppercase() } ) {
            return -1 // Kotlin way of doing above for loop in 1 line of code
        }

        // notify MainActivity of location in list by returning Int position
        // implement add at position
        if (position == null) {
            places.add(place) // adds at end currently
            return places.lastIndex // what's the number of the last index in the list
        } else {
            places.add(position, place)
            return position
        }


    }

    fun movePlace(from: Int, to: Int) {
        val place = places.removeAt(from)
        places.add(to, place)
        Log.d(TAG, places.toString())
    }

    fun deletePlace(position: Int): Place {
        return places.removeAt(position)
    }
}