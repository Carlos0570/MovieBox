package com.example.moviebox.serieDetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.core.data.dataClasses.Cast
import com.example.moviebox.core.data.dataClasses.Serie
import com.example.moviebox.core.data.dataClasses.ProvidersByCountry
import com.example.moviebox.core.data.dataClasses.Trailer
import com.example.moviebox.core.network.Result
import com.example.moviebox.core.presentation.screenStates.ScreenState
import com.example.moviebox.serieDetail.domain.SerieCreditsUseCase
import com.example.moviebox.serieDetail.domain.SerieDetailUseCase
import com.example.moviebox.serieDetail.domain.SerieProvidersUseCase
import com.example.moviebox.serieDetail.domain.SimilarSeriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.moviebox.core.util.awaitAll
import com.example.moviebox.serieDetail.domain.SerieTrailersUseCase
import java.io.IOException

@HiltViewModel
class SerieDetailViewModel @Inject constructor(
    private val serieDetailUseCase: SerieDetailUseCase,
    private val serieCreditsUseCase: SerieCreditsUseCase,
    private val similarSeriesUseCase: SimilarSeriesUseCase,
    private val seriesProvidersUseCase: SerieProvidersUseCase,
    private val serieTrailersUseCase: SerieTrailersUseCase
) : ViewModel() {

    val state = MutableStateFlow<ScreenState>(ScreenState.LOADING)

    private val _serieDetail = MutableStateFlow<Serie?>(null)
    val serieDetail = _serieDetail

    private val _serieCast = MutableStateFlow<List<Cast>>(emptyList())
    val serieCast = _serieCast

    private val _similarSeries = MutableStateFlow<List<Serie>>(emptyList())
    val similarSeries = _similarSeries

    private val _serieProviders = MutableStateFlow<ProvidersByCountry?>(null)
    val serieProviders = _serieProviders

    private val _trailers = MutableStateFlow<List<Trailer>>(emptyList())
    var trailers = _trailers

    private val _trailerId = MutableStateFlow<String?>(null)
    val trailerId = _trailerId

    private val _showTrailer = MutableStateFlow(false)
    var showTrailer = _showTrailer

    fun initSerieDetail(serieId: Int) {
        viewModelScope.launch {
            state.value = ScreenState.LOADING
            awaitAll(
                launch { getSerieDetail(serieId) },
                launch { getSerieCredits(serieId) },
                launch { getSimilarSeries(serieId) },
                launch { getSerieProviders(serieId) },
                launch { getSerieTrailers(serieId) }
            )
            if (state.value is ScreenState.LOADING)
                state.value = ScreenState.SUCCESS
        }
    }

    fun showMovieTrailer(trailerId: String) {
        _showTrailer.value = true
        _trailerId.value = trailerId
    }

    fun dismissTrailer() {
        _showTrailer.value = false
        _trailerId.value = null
    }

    private suspend fun getSerieTrailers(serieId: Int) {
        when (val result = serieTrailersUseCase(serieId)) {
            is Result.Error -> if (result.exception is IOException)
                state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> trailers.value = result.data
        }
    }

    private suspend fun getSerieDetail(serieId: Int) {
        when (val result = serieDetailUseCase(serieId)) {
            is Result.Error -> if (result.exception is IOException)
                state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _serieDetail.value = result.data as Serie
        }
    }

    private suspend fun getSerieCredits(serieId: Int) {
        when (val result = serieCreditsUseCase(serieId)) {
            is Result.Error -> if (result.exception is IOException)
                state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _serieCast.value = result.data
        }
    }

    private suspend fun getSimilarSeries(serieId: Int) {
        when (val result = similarSeriesUseCase(serieId)) {
            is Result.Error -> if (result.exception is IOException)
                state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _similarSeries.value = result.data
        }
    }

    private suspend fun getSerieProviders(serieId: Int) {
        when (val result = seriesProvidersUseCase(serieId)) {
            is Result.Error -> if (result.exception is IOException)
                state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> _serieProviders.value = result.data
        }
    }
}