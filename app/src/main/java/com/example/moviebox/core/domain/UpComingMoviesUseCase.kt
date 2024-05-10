package com.example.moviebox.core.domain

import com.example.moviebox.core.data.MovieBoxRepository
import com.example.moviebox.core.data.dataClasses.Movie
import com.example.moviebox.core.network.Result
import javax.inject.Inject

class UpComingMoviesUseCase  @Inject constructor(private val repository: MovieBoxRepository){

    suspend operator fun invoke () : Result<List<Movie>> = repository.getUpcomingMovies()
}