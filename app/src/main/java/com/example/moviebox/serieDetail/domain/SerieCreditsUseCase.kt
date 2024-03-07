package com.example.moviebox.serieDetail.domain

import com.example.moviebox.core.data.MovieBoxRepository
import javax.inject.Inject

class SerieCreditsUseCase @Inject constructor(private val repository: MovieBoxRepository) {

    suspend operator fun invoke(serieId: Int) = repository.getSerieCredits(serieId)
}