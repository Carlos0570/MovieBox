package com.example.moviebox.core.domain

import com.example.moviebox.core.data.MovieBoxRepository
import javax.inject.Inject

class MovieImagesUseCase @Inject constructor(private val repository: MovieBoxRepository) {
    suspend operator fun invoke(movieId: Int) = repository.getMovieImages(movieId)
}