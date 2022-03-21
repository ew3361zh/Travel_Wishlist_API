package com.nikosnockoffs.android.travel_wishlist

import java.text.SimpleDateFormat
import java.util.*

class Place(val name: String, val reason: String? = null, var starred: Boolean = false, val id: Int? = null) { // add default for Date field to set to right now if we don't set a specific date
//    fun formattedDate(): String {
//        return SimpleDateFormat("EEE, d MMMM yyyy", Locale.getDefault()).format(dateAdded)
//    }

    override fun toString(): String {
        return "$name $reason"
    }
}