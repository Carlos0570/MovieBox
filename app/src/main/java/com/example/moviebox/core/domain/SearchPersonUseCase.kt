package com.example.moviebox.core.domain

import com.example.moviebox.core.data.MovieBoxRepository
import javax.inject.Inject

class SearchPersonUseCase @Inject constructor(private val repository: MovieBoxRepository) {
    suspend operator fun invoke(personName: String) = repository.searchPerson(personName)
}