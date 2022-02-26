package com.nikosnockoffs.android.travel_wishlist

import java.text.SimpleDateFormat
import java.util.*

class Place(val name: String, val reason: String, val dateAdded: Date = Date()) { // add default for Date field to set to right now if we don't set a specific date
    fun formattedDate(): String {
        return SimpleDateFormat("EEE, d MMMM yyyy", Locale.getDefault()).format(dateAdded)
    }

    override fun toString(): String {
        return "$name ${formattedDate()} $reason"
    }
}