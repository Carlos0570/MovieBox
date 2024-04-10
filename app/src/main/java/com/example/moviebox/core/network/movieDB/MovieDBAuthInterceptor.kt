package com.example.moviebox.core.network.movieDB

import com.example.moviebox.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class MovieDBAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder =
            chain
                .request()
                .newBuilder()
                .addHeader("Authorization", "Bearer ${BuildConfig.MOVIE_DB_KEY}")
                .build()
        return chain.proceed(requestBuilder)
    }
}