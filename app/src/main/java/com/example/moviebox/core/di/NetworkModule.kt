package com.example.moviebox.core.di

import com.example.moviebox.core.network.AuthInterceptor
import com.example.moviebox.core.network.MovieBoxClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .client(okhttpClient())
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideMoviesClient(retrofit: Retrofit): MovieBoxClient =
        retrofit.create(MovieBoxClient::class.java)

    private fun okhttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor())
            .build()
}

object Constants {
    const val BASE_URL = "https://api.themoviedb.org/3/"
}