package com.example.moviebox.core.domain

import com.example.moviebox.core.data.MovieBoxRepository
import javax.inject.Inject

class MovieProvidersUseCase @Inject constructor(private val repository: MovieBoxRepository) {
    suspend operator fun invoke(movieID: Int) = repository.getMovieProviders(movieID)
}