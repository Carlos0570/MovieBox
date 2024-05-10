package com.example.moviebox.core.network.movieDB

import com.example.moviebox.core.data.dataClasses.Movie
import com.example.moviebox.core.data.dataClasses.Serie
import com.example.moviebox.core.data.dataClasses.Credits
import com.example.moviebox.core.data.dataClasses.MediaImage
import com.example.moviebox.core.data.dataClasses.MovieProvidersResponse
import com.example.moviebox.core.data.dataClasses.MovieBoxResponse
import com.example.moviebox.core.data.dataClasses.Person
import com.example.moviebox.core.data.dataClasses.Trailer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieBoxClient {
    @GET("trending/movie/day?language=en-US")
    suspend fun getTrendingMovies(): Response<MovieBoxResponse<Movie>>

    @GET("movie/upcoming?language=en-US&page=1")
    suspend fun upComingMovies(): Response<MovieBoxResponse<Movie>>

    @GET("movie/top_rated?language=en-US&page=1")
    suspend fun getTopRatedMovies(): Response<MovieBoxResponse<Movie>>

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") movieId: Int): Response<Movie>

    @GET("movie/{movie_id}/credits?language=en-US")
    suspend fun getMovieCredits(@Path("movie_id") movieId: Int): Response<Credits>

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") movieId: Int): Response<MovieBoxResponse<Movie>>

    @GET("movie/{movie_id}/watch/providers")
    suspend fun getMovieProviders(@Path("movie_id") movieId: Int): Response<MovieProvidersResponse>

    @GET("tv/{series_id}")
    suspend fun getSerieDetail(@Path("series_id") serieId: Int): Response<Serie>

    @GET("tv/{series_id}/credits")
    suspend fun getSerieCredits(@Path("series_id") serieId: Int): Response<Credits>

    @GET("tv/{series_id}/recommendations")
    suspend fun getSimilarSeries(@Path("series_id") serieId: Int): Response<MovieBoxResponse<Serie>>

    @GET("tv/{series_id}/watch/providers")
    suspend fun getSerieProviders(@Path("series_id") serieId: Int): Response<MovieProvidersResponse>

    @GET("trending/tv/day?language=en-US")
    suspend fun getTrendingSeries(): Response<MovieBoxResponse<Serie>>

    @GET("tv/popular?language=en-US&page=1")
    suspend fun getPopularSeries(): Response<MovieBoxResponse<Serie>>

    @GET("tv/top_rated?language=en-US&page=1")
    suspend fun topRatedSeries(): Response<MovieBoxResponse<Serie>>

    @GET("tv/on_the_air?language=en-US&page=1")
    suspend fun onTheAir(): Response<MovieBoxResponse<Serie>>

    @GET("trending/person/day?language=en-US")
    suspend fun getTrendingPerson(): Response<MovieBoxResponse<Person>>

    @GET("person/{person_id}/combined_credits")
    suspend fun getPersonCredits(@Path("person_id") personId: Int): Response<Credits>

    @GET("person/{person_id}")
    suspend fun getPersonDetails(@Path("person_id") personId: Int): Response<Person>

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieBoxResponse<Movie>>

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("language") language: String = "en-US"
    ): Response<MovieBoxResponse<Movie>>

    @GET("search/tv")
    suspend fun searchSerie(
        @Query("query") query: String,
        @Query("language") language: String = "en-US"
    ): Response<MovieBoxResponse<Serie>>

    @GET("search/person")
    suspend fun searchPerson(
        @Query("query") query: String,
        @Query("language") language: String = "en-US"
    ): Response<MovieBoxResponse<Person>>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieTrailers(@Path("movie_id") movieId: Int): Response<MovieBoxResponse<Trailer>>

    @GET("tv/{series_id}/videos")
    suspend fun getSerieTrailers(@Path("series_id") serieId: Int): Response<MovieBoxResponse<Trailer>>

    @GET("movie/{movie_id}/images")
    suspend fun getMovieImages(@Path("movie_id") movieId: Int): Response<MediaImage>

    @GET("tv/{series_id}/images")
    suspend fun getSerieImages(@Path("series_id") serieId: Int): Response<MediaImage>
}