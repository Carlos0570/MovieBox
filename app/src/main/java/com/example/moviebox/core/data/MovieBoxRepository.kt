package com.example.moviebox.core.data

import com.example.moviebox.core.data.dataClasses.Trailer
import com.example.moviebox.core.network.Result
import com.example.moviebox.core.network.movieDB.MoviesService
import com.example.moviebox.core.network.youtube.YoutubeService
import javax.inject.Inject

class MovieBoxRepository @Inject constructor(
    private val movieDB: MoviesService,
    private val youtubeAPI: YoutubeService
) {
    suspend fun getTrendingMovies() = movieDB.getTrendingMovies()

    suspend fun getPopularMovies() = movieDB.getPopularMovies()

    suspend fun getUpcomingMovies() = movieDB.getUpComingMovies()

    suspend fun getTopRatedMovies() = movieDB.getTopRatedMovies()

    suspend fun getMovieDetails(movieID: Int) = movieDB.getMovieDetails(movieID)

    suspend fun getMovieCredits(movieId: Int) = movieDB.getMovieCredits(movieId)

    suspend fun getSimilarMovies(movieId: Int) = movieDB.getSimilarMovies(movieId)

    suspend fun getMovieProviders(movieId: Int) = movieDB.getMovieProviders(movieId)

    suspend fun getTrendingSeries() = movieDB.getTrendingSeries()

    suspend fun getPopularSeries() = movieDB.getPopularSeries()

    suspend fun getTopRatedSeries() = movieDB.getTopRatedSeries()

    suspend fun getTrendingPersons() = movieDB.getTrendingPersons()

    suspend fun getOnAirSeries() = movieDB.getOnAirSeries()

    suspend fun getSerieDetail(serieId: Int) = movieDB.getSerieDetail(serieId)

    suspend fun getSerieCredits(serieId: Int) = movieDB.getSerieCredits(serieId)

    suspend fun getSimilarSeries(serieId: Int) = movieDB.getSimilarSeries(serieId)

    suspend fun getSerieProviders(serieId: Int) = movieDB.getSeriesProviders(serieId)

    suspend fun getPersonCredits(personId: Int) = movieDB.getPersonCredits(personId)

    suspend fun getPersonDetails(personId: Int) = movieDB.getPersonDetails(personId)

    suspend fun searchMovie(movieName: String) = movieDB.searchMovie(movieName)

    suspend fun searchSerie(serieName: String) = movieDB.searchSerie(serieName)

    suspend fun searchPerson(personName: String) = movieDB.searchPerson(personName)

    suspend fun getMovieTrailers(movieId: Int): Result<List<Trailer>> {
        when (val result = movieDB.getMovieTrailers(movieId)) {
            is Result.Error -> return Result.Error(result.exception)
            is Result.Success -> {
                return Result.Success(
                    result.data.map {
                        when (val youtubeData = youtubeAPI.getVideoDetails(it.key.toString())) {
                            is Result.Error -> return Result.Error(youtubeData.exception)

                            is Result.Success ->  it.copy(youtubeData = youtubeData.data.body())
                        }
                    }
                )
            }
        }
    }

    suspend fun getSerieTrailers(serieId: Int): Result<List<Trailer>> {
        when (val result = movieDB.getSerieTrailers(serieId)) {
            is Result.Error -> return Result.Error(result.exception)
            is Result.Success -> {
                return Result.Success(
                    result.data.map {
                        when (val youtubeData = youtubeAPI.getVideoDetails(it.key.toString())) {
                            is Result.Error -> return Result.Error(youtubeData.exception)

                            is Result.Success ->  it.copy(youtubeData = youtubeData.data.body())
                        }
                    }
                )
            }
        }
    }
}