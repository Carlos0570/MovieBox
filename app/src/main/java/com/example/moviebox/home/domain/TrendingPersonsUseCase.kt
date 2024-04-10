package com.example.moviebox.home.domain

import com.example.moviebox.core.data.MovieBoxRepository
import javax.inject.Inject

class TrendingPersonsUseCase @Inject constructor(private val repository: MovieBoxRepository) {
    suspend operator fun invoke() = repository.getTrendingPersons()
}