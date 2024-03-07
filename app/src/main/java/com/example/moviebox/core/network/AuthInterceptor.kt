package com.example.moviebox.core.network

import com.example.moviebox.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder =
            chain
                .request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
                .build()
        return chain.proceed(requestBuilder)
    }
}