package com.example.moviebox.moviedetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.core.data.dataClasses.Cast
import com.example.moviebox.core.data.dataClasses.Movie
import com.example.moviebox.core.data.dataClasses.ProvidersByCountry
import com.example.moviebox.core.network.Result
import com.example.moviebox.core.presentation.screenStates.ScreenState
import com.example.moviebox.core.util.awaitAll
import com.example.moviebox.moviedetail.domain.MovieCreditsUseCase
import com.example.moviebox.moviedetail.domain.MovieDetailsUseCase
import com.example.moviebox.moviedetail.domain.MovieProvidersUseCase
import com.example.moviebox.moviedetail.domain.SimilarMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieDetailUseCase: MovieDetailsUseCase,
    private val movieCreditsUseCase: MovieCreditsUseCase,
    private val similarMoviesUseCase: SimilarMoviesUseCase,
    private val movieProvidersUseCase: MovieProvidersUseCase
) : ViewModel() {
    val state = MutableStateFlow<ScreenState>(ScreenState.LOADING)

    private val _movieDetail = MutableStateFlow<Movie?>(null)
    val movieDetail = _movieDetail

    private val _movieCast = MutableStateFlow<List<Cast>>(emptyList())
    val movieCast = _movieCast

    private val _similarMovies = MutableStateFlow<List<Movie>>(emptyList())
    val similarMovies = _similarMovies

    private val _movieProviders = MutableStateFlow<ProvidersByCountry?>(null)
    val movieProviders = _movieProviders

    fun initMovieDetail(movieId: Int) {
        viewModelScope.launch {
            state.value = ScreenState.LOADING
            awaitAll(
                launch { getMovieDetail(movieId) },
                launch { getMovieCast(movieId) },
                launch { getSimilarMovies(movieId) },
                launch { getMovieProviders(movieId) }
            )
            if (state.value is ScreenState.LOADING)
                state.value = ScreenState.SUCCESS
        }
    }

    private suspend fun getMovieDetail(movieId: Int) {
        when (val result = movieDetailUseCase(movieId)) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> movieDetail.value = result.data
        }
    }

    private suspend fun getMovieCast(movieId: Int) {
        when (val result = movieCreditsUseCase(movieId)) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _movieCast.value = result.data
        }
    }

    private suspend fun getSimilarMovies(movieId: Int) {
        when (val result = similarMoviesUseCase(movieId)) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> similarMovies.value = result.data
        }
    }

    private suspend fun getMovieProviders(movieId: Int) {
        when (val result = movieProvidersUseCase(movieId)) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> movieProviders.value = result.data
        }
    }
}