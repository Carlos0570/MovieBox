package com.example.moviebox.moviedetail.domain

import com.example.moviebox.core.data.MovieBoxRepository
import javax.inject.Inject

class MovieCreditsUseCase @Inject constructor(private val repository: MovieBoxRepository) {
    suspend operator fun invoke(movieId: Int) = repository.getMovieCredits(movieId)
}