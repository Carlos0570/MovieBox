package com.example.moviebox.core.domain

import com.example.moviebox.core.data.MovieBoxRepository
import javax.inject.Inject

class TrendingMoviesUseCase @Inject constructor(private val repository: MovieBoxRepository) {
    suspend operator fun invoke() = repository.getTrendingMovies()
}