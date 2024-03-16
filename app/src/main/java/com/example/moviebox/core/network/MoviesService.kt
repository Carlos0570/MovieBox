package com.example.moviebox.core.network

import com.example.moviebox.core.data.dataClasses.Cast
import com.example.moviebox.core.data.dataClasses.Credits
import com.example.moviebox.core.data.dataClasses.Movie
import com.example.moviebox.core.data.dataClasses.Person
import com.example.moviebox.core.data.dataClasses.Serie
import com.example.moviebox.core.data.dataClasses.ProvidersByCountry
import javax.inject.Inject

class MoviesService @Inject constructor(private val movieBoxClient: MovieBoxClient) {

    suspend fun getTrendingMovies(): Result<List<Movie>> = safeAPICall {
        movieBoxClient.getTrendingMovies().body()?.results ?: emptyList()
    }

    suspend fun getPopularMovies(): Result<List<Movie>> = safeAPICall {
        movieBoxClient.getPopularMovies().body()?.results ?: emptyList()
    }

    suspend fun getUpComingMovies(): Result<List<Movie>> = safeAPICall {
        movieBoxClient.upComingMovies().body()?.results ?: emptyList()
    }

    suspend fun getOnAirSeries(): Result<List<Serie>> = safeAPICall {
        movieBoxClient.onTheAir().body()?.results ?: emptyList()
    }

    suspend fun getTopRatedMovies(): Result<List<Movie>> = safeAPICall {
        movieBoxClient.getTopRatedMovies().body()?.results ?: emptyList()
    }

    suspend fun getMovieDetails(movieId: Int): Result<Movie?> = safeAPICall {
        movieBoxClient.getMovieDetails(movieId).body()
    }

    suspend fun getMovieCredits(movieId: Int): Result<List<Cast>> = safeAPICall {
        movieBoxClient.getMovieCredits(movieId).body()?.cast ?: emptyList()
    }

    suspend fun getSimilarMovies(movieId: Int): Result<List<Movie>> = safeAPICall {
        movieBoxClient.getSimilarMovies(movieId).body()?.results ?: emptyList()
    }

    suspend fun getMovieProviders(movieId: Int): Result<ProvidersByCountry?> = safeAPICall {
        movieBoxClient.getMovieProviders(movieId).body()?.providersByCountry
            ?: ProvidersByCountry()
    }

    suspend fun getPopularSeries(): Result<List<Serie>> = safeAPICall {
        movieBoxClient.getPopularSeries().body()?.results ?: emptyList()
    }

    suspend fun getTrendingSeries(): Result<List<Serie>> = safeAPICall {
        movieBoxClient.getTrendingSeries().body()?.results ?: emptyList()
    }

    suspend fun getTopRatedSeries(): Result<List<Serie>> = safeAPICall {
        movieBoxClient.topRatedSeries().body()?.results ?: emptyList()
    }

    suspend fun getSerieDetail(serieId: Int): Result<Serie?> = safeAPICall {
        movieBoxClient.getSerieDetail(serieId).body()
    }

    suspend fun getSimilarSeries(serieId: Int): Result<List<Serie>> = safeAPICall {
        movieBoxClient.getSimilarSeries(serieId).body()?.results ?: emptyList()
    }

    suspend fun getSerieCredits(serieId: Int): Result<List<Cast>> = safeAPICall {
        movieBoxClient.getSerieCredits(serieId).body()?.cast ?: emptyList()
    }

    suspend fun getSeriesProviders(serieId: Int): Result<ProvidersByCountry?> = safeAPICall {
        movieBoxClient.getSerieProviders(serieId).body()
            ?.providersByCountry ?: ProvidersByCountry()
    }

    suspend fun getPersonCredits(personId: Int): Result<Credits?> = safeAPICall {
        movieBoxClient.getPersonCredits(personId).body()
    }

    suspend fun getPersonDetails(personId: Int): Result<Person?> = safeAPICall {
        movieBoxClient.getPersonDetails(personId).body()
    }

    suspend fun searchMovie(movieName: String): Result<List<Movie>> = safeAPICall {
        movieBoxClient.searchMovie(movieName).body()?.results ?: emptyList()
    }

    suspend fun searchSerie(serieName: String): Result<List<Serie>> = safeAPICall {
        movieBoxClient.searchSerie(serieName).body()?.results ?: emptyList()
    }

    suspend fun searchPerson(personName: String): Result<List<Person>> = safeAPICall {
        movieBoxClient.searchPerson(personName).body()?.results ?: emptyList()
    }
}


