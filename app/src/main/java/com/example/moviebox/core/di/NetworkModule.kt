package com.example.moviebox.core.di

import com.example.moviebox.core.network.movieDB.MovieDBAuthInterceptor
import com.example.moviebox.core.network.movieDB.MovieBoxClient
import com.example.moviebox.core.network.youtube.YoutubeAuthInterceptor
import com.example.moviebox.core.network.youtube.YoutubeClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Named("MovieDBRetrofit")
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .client(okhttpClient(MovieDBAuthInterceptor()))
            .baseUrl(Constants.BASE_THE_MOVIE_DB_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideMoviesClient(@Named("MovieDBRetrofit") retrofit: Retrofit): MovieBoxClient =
        retrofit.create(MovieBoxClient::class.java)

    @Named("YoutubeRetrofit")
    @Singleton
    @Provides
    fun provideYoutubeRetrofit(): Retrofit =
        Retrofit.Builder()
            .client(okhttpClient(YoutubeAuthInterceptor()))
            .baseUrl(Constants.BASE_YOUTUBE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideYoutubeClient(@Named("YoutubeRetrofit") retrofit: Retrofit): YoutubeClient =
        retrofit.create(YoutubeClient::class.java)

    private fun okhttpClient(interceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build()
}

object Constants {
    const val BASE_THE_MOVIE_DB_URL = "https://api.themoviedb.org/3/"
    const val BASE_YOUTUBE_URL = "https://youtube.googleapis.com/youtube/v3/"
}