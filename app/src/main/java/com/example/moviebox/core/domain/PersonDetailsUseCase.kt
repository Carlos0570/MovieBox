package com.example.moviebox.core.domain

import com.example.moviebox.core.data.MovieBoxRepository
import javax.inject.Inject

class PersonDetailsUseCase @Inject constructor( private val repository: MovieBoxRepository) {

    suspend operator fun invoke(personId : Int) = repository.getPersonDetails(personId)
}