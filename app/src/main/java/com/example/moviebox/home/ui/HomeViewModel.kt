package com.example.moviebox.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.core.data.dataClasses.Movie
import com.example.moviebox.core.data.dataClasses.Person
import com.example.moviebox.core.data.dataClasses.Serie
import com.example.moviebox.core.network.Result
import com.example.moviebox.core.presentation.screenStates.ScreenState
import com.example.moviebox.core.util.awaitAll
import com.example.moviebox.home.domain.OnAirUseCase
import com.example.moviebox.home.domain.PopularSeriesUseCase
import com.example.moviebox.home.domain.TopRatedMoviesUseCase
import com.example.moviebox.home.domain.TopRatedSeriesUseCase
import com.example.moviebox.home.domain.TrendingMoviesUseCase
import com.example.moviebox.home.domain.TrendingPersonsUseCase
import com.example.moviebox.home.domain.TrendingSeriesUseCase
import com.example.moviebox.home.domain.UpComingMoviesUseCase
import com.example.moviebox.searchscreen.domain.PopularMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val trendingMoviesUseCase: TrendingMoviesUseCase,
    private val trendingSeriesUseCase: TrendingSeriesUseCase,
    private val popularSeriesUseCase: PopularSeriesUseCase,
    private val topRatedSeriesUseCase: TopRatedSeriesUseCase,
    private val upComingMoviesUseCase: UpComingMoviesUseCase,
    private val topRatedMoviesUseCase: TopRatedMoviesUseCase,
    private val trendingPersonsUseCase: TrendingPersonsUseCase,
    val popularMoviesUseCase: PopularMoviesUseCase,
    private val onAirUseCase: OnAirUseCase,
) : ViewModel() {

    private var _state = MutableStateFlow<ScreenState>(ScreenState.LOADING)
    val state = _state

    private val _trendingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val trendingMovies: StateFlow<List<Movie>> get() = _trendingMovies

    private val _trendingSeries = MutableStateFlow<List<Serie>>(emptyList())
    val trendingSeries = _trendingSeries

    private val _popularSeries = MutableStateFlow<List<Serie>>(emptyList())
    val popularSeries = _popularSeries

    private val _topRatedSeries = MutableStateFlow<List<Serie>>(emptyList())
    val topRatedSeries = _topRatedSeries

    private val _onAirSeries = MutableStateFlow<List<Serie>>(emptyList())
    val onAirSeries = _onAirSeries

    private val _upComingMovies = MutableStateFlow<List<Movie>>(emptyList())
    val upComingMovies = _upComingMovies

    private val _topRatedMovies = MutableStateFlow<List<Movie>>(emptyList())
    val topRatedMovies = _topRatedMovies

    private val _recommendedMovie = MutableStateFlow<Movie?>(null)
    val recommendedMovie = _recommendedMovie

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies = _popularMovies

    private val _trendingPersons = MutableStateFlow<List<Person>>(emptyList())
    var trendingPersons = _trendingPersons

    private var _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    init {
        initHomeScreenData()
    }

    fun initHomeScreenData() {
        viewModelScope.launch {
            state.value = ScreenState.LOADING
            getData()
            if (state.value is ScreenState.LOADING)
                state.value = ScreenState.SUCCESS
        }
    }

    fun refreshHomeScreen() {
        viewModelScope.launch {
            _isRefreshing.value = true
            getData()
            _isRefreshing.value = false
        }
    }

    private fun getData() {
        viewModelScope.launch {
            awaitAll(
                launch { getUpcomingMovies() },
                launch { getTrendingSeries() },
                launch { getTopRatedMovies() },
                launch { getUpComingMovies() },
                launch { getOnAirSeries() },
                launch { getTopRatedSeries() },
                launch { getPopularSeries() },
                launch { getTrendingMovies() },
                launch { getPopularMovies() },
                launch { getTrendingPersons() }
            )
        }
    }

    private suspend fun getTopRatedMovies() {
        when (val result = topRatedMoviesUseCase()) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _topRatedMovies.value = result.data
        }
    }

    private suspend fun getTrendingPersons() {
        when (val result = trendingPersonsUseCase()) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _trendingPersons.value = result.data
        }
    }

    private suspend fun getUpComingMovies() {
        when (val result = upComingMoviesUseCase()) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _upComingMovies.value = result.data
        }
    }

    private suspend fun getOnAirSeries() {
        when (val result = onAirUseCase()) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _onAirSeries.value = result.data
        }
    }

    private suspend fun getTopRatedSeries() {
        when (val result = topRatedSeriesUseCase()) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _topRatedSeries.value = result.data
        }
    }

    private suspend fun getPopularSeries() {
        when (val result = popularSeriesUseCase()) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _popularSeries.value = result.data
        }
    }

    private suspend fun getUpcomingMovies() {
        when (val result = upComingMoviesUseCase()) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _upComingMovies.value = result.data
        }
    }

    private suspend fun getTrendingSeries() {
        when (val result = trendingSeriesUseCase()) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _trendingSeries.value = result.data
        }
    }

    private suspend fun getTrendingMovies() {
        when (val result = trendingMoviesUseCase()) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> {
                _trendingMovies.value = result.data
                _recommendedMovie.value = _trendingMovies.value.random()
            }
        }
    }

    private suspend fun getPopularMovies() {
        when (val result = popularMoviesUseCase()) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> {
                _popularMovies.value = result.data
            }
        }
    }
}

