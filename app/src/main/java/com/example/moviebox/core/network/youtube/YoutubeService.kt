package com.example.moviebox.core.network.youtube

import com.example.moviebox.core.data.dataClasses.YoutubeData
import com.example.moviebox.core.network.Result
import com.example.moviebox.core.network.safeAPICall
import retrofit2.Response
import javax.inject.Inject

class YoutubeService @Inject constructor(private val youtubeClient: YoutubeClient) {
    suspend fun getVideoDetails(id: String): Result<Response<YoutubeData>> = safeAPICall {
        youtubeClient.getVideoDetails(
            part = "snippet",
            id = id
        )
    }
}