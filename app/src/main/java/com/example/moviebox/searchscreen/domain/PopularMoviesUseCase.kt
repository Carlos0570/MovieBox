package com.example.moviebox.searchscreen.domain

import com.example.moviebox.core.data.MovieBoxRepository
import javax.inject.Inject

class PopularMoviesUseCase @Inject constructor(private val repository: MovieBoxRepository) {
    suspend operator fun invoke() = repository.getPopularMovies()
}