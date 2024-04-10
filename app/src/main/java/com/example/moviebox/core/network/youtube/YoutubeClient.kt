package com.example.moviebox.core.network.youtube

import com.example.moviebox.core.data.dataClasses.YoutubeData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeClient {
    @GET("videos")
    suspend fun getVideoDetails(
        @Query("part")  part: String,
        @Query("id") id: String
    ): Response<YoutubeData>
}