package com.example.moviebox.core.network.youtube

import com.example.moviebox.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class YoutubeAuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val requestBuilder = chain.request().url.newBuilder()
            .addQueryParameter("key", BuildConfig.YOUTUBE_KEY)
            .build()

        val request = chain.request().newBuilder()
            // .addHeader("Authorization", "Bearer token")
            .url(requestBuilder)
            .build()
        val requestBuilder2 =
            chain
                .request()
                .newBuilder()

                .addHeader("key", BuildConfig.YOUTUBE_KEY)
                .build()
        return chain.proceed(request)
    }
}