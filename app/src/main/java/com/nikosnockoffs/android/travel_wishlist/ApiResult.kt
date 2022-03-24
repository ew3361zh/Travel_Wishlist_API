package com.nikosnockoffs.android.travel_wishlist

// status of request - success, server error, network error
// data, if any
// message to user if applicable

enum class ApiStatus {
    SUCCESS,
    SERVER_ERROR,
    NETWORK_ERROR
}

// T is a placeholder for a Kotlin type
data class ApiResult<out T>(val status: ApiStatus, val data: T?, val message: String?) {
}