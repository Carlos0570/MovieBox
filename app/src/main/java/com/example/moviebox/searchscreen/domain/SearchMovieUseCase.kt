package com.example.moviebox.searchscreen.domain

import com.example.moviebox.core.data.MovieBoxRepository
import javax.inject.Inject

class SearchMovieUseCase @Inject constructor(private val repository: MovieBoxRepository) {
    suspend operator fun invoke(movieName: String) = repository.searchMovie(movieName)
}