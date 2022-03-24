package com.nikosnockoffs.android.travel_wishlist

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationHeaderInterceptor: Interceptor { // class that can intercept http requests and add info necessary
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithHeaders = chain.request().newBuilder()
            .addHeader("Authorization", "Token ${BuildConfig.PLACES_TOKEN}") // authentication
            .build()

        return chain.proceed(requestWithHeaders)
    }
}