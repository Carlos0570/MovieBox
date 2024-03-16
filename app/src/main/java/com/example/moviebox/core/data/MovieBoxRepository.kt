package com.example.moviebox.core.data

import com.example.moviebox.core.data.dataClasses.Movie
import com.example.moviebox.core.network.MoviesService
import javax.inject.Inject

class MovieBoxRepository @Inject constructor(private val api: MoviesService) {

    suspend fun getTrendingMovies() = api.getTrendingMovies()

    suspend fun getPopularMovies() = api.getPopularMovies()
    suspend fun getUpcomingMovies() = api.getUpComingMovies()

    suspend fun getTopRatedMovies() = api.getTopRatedMovies()

    suspend fun getMovieDetails(movieID: Int) = api.getMovieDetails(movieID)

    suspend fun getMovieCredits(movieId: Int) = api.getMovieCredits(movieId)

    suspend fun getSimilarMovies(movieId: Int) = api.getSimilarMovies(movieId)

    suspend fun getMovieProviders(movieId: Int) = api.getMovieProviders(movieId)

    suspend fun getTrendingSeries() = api.getTrendingSeries()

    suspend fun getPopularSeries() = api.getPopularSeries()

    suspend fun getTopRatedSeries() = api.getTopRatedSeries()

    suspend fun getOnAirSeries() = api.getOnAirSeries()

    suspend fun getSerieDetail(serieId: Int) = api.getSerieDetail(serieId)

    suspend fun getSerieCredits(serieId: Int) = api.getSerieCredits(serieId)

    suspend fun getSimilarSeries(serieId: Int) = api.getSimilarSeries(serieId)

    suspend fun getSerieProviders(serieId: Int) = api.getSeriesProviders(serieId)

    suspend fun getPersonCredits(personId: Int) = api.getPersonCredits(personId)

    suspend fun getPersonDetails(personId: Int) = api.getPersonDetails(personId)

    suspend fun searchMovie(movieName: String) = api.searchMovie(movieName)

    suspend fun searchSerie(serieName: String) = api.searchSerie(serieName)

    suspend fun searchPerson(personName: String) = api.searchPerson(personName)
}