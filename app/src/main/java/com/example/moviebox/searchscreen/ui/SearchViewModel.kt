package com.example.moviebox.searchscreen.ui

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.core.data.dataClasses.Movie
import com.example.moviebox.core.data.dataClasses.Person
import com.example.moviebox.core.data.dataClasses.Serie
import com.example.moviebox.core.network.Result
import com.example.moviebox.core.presentation.screenStates.SearchScreenState
import com.example.moviebox.core.util.awaitAll
import com.example.moviebox.searchscreen.domain.PopularMoviesUseCase
import com.example.moviebox.searchscreen.domain.SearchMovieUseCase
import com.example.moviebox.searchscreen.domain.SearchPersonUseCase
import com.example.moviebox.searchscreen.domain.SearchSerieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    val popularMoviesUseCase: PopularMoviesUseCase,
    val searchMovieUseCase: SearchMovieUseCase,
    val searchSerieUseCase: SearchSerieUseCase,
    val searchPersonUseCase: SearchPersonUseCase
) : ViewModel() {
    val state = MutableStateFlow<SearchScreenState>(SearchScreenState.LOADING)

    private val _searchText = mutableStateOf("")
    var searchText = _searchText

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies = _movies

    private val _series = MutableStateFlow<List<Serie>>(emptyList())
    val series = _series

    private val _persons = MutableStateFlow<List<Person>>(emptyList())
    val persons = _persons

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies = _popularMovies
    fun init() {
        viewModelScope.launch {
            state.value = SearchScreenState.LOADING
            awaitAll(
                launch { getPopularMovies() }
            )
            if (state.value is SearchScreenState.LOADING)
                state.value = SearchScreenState.SUCCESS
        }
    }

    fun search() {
        viewModelScope.launch {
            state.value = SearchScreenState.LOADING
            awaitAll(
                launch { searchMovie() },
                launch { searchSerie() },
                launch { searchPersons() }
            )
            if (_movies.value.isEmpty() && _series.value.isEmpty() && _persons.value.isEmpty())
                state.value = SearchScreenState.NOT_FOUND

            if (state.value is SearchScreenState.LOADING)
                state.value = SearchScreenState.SUCCESS
        }
    }

    fun clearData() {
        _searchText.value = ""
        state.value = SearchScreenState.SUCCESS
        _movies.value = emptyList()
        _series.value = emptyList()
        _persons.value = emptyList()
    }

    private suspend fun getPopularMovies() {
        when (val result = popularMoviesUseCase()) {
            is Result.Error ->
                if (result.exception is IOException)
                    state.value = SearchScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _popularMovies.value = result.data
        }
    }

    private suspend fun searchMovie() {
        when (val result = searchMovieUseCase(_searchText.value)) {
            is Result.Error -> if (result.exception is IOException)
                state.value = SearchScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _movies.value = result.data
        }
    }

    private suspend fun searchSerie() {
        when (val result = searchSerieUseCase(_searchText.value)) {
            is Result.Error -> if (result.exception is IOException)
                state.value = SearchScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _series.value = result.data
        }
    }

    private suspend fun searchPersons() {
        when (val result = searchPersonUseCase(_searchText.value)) {
            is Result.Error -> if (result.exception is IOException)
                state.value = SearchScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _persons.value = result.data
        }
    }
}