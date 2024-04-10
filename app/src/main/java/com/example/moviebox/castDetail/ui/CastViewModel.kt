package com.example.moviebox.castDetail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moviebox.castDetail.domain.PersonCreditsUseCase
import com.example.moviebox.castDetail.domain.PersonDetailsUseCase
import com.example.moviebox.core.data.dataClasses.Credits
import com.example.moviebox.core.data.dataClasses.Person
import com.example.moviebox.core.network.Result
import com.example.moviebox.core.presentation.screenStates.ScreenState
import com.example.moviebox.core.util.awaitAll
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class CastViewModel @Inject constructor(
    val personCreditsUseCase: PersonCreditsUseCase,
    val personDetailsUseCase: PersonDetailsUseCase
) :
    ViewModel() {
    val state = MutableStateFlow<ScreenState>(ScreenState.LOADING)
    private val _personCredits = MutableStateFlow<Credits?>(null)
    val personCredits = _personCredits

    private val _personDetails = MutableStateFlow<Person?>(null)
    val personDetails = _personDetails

    fun initCastDetail(castId: Int) {
        viewModelScope.launch {
            state.value = ScreenState.LOADING
            awaitAll(
                launch { getPersonCredits(castId) },
                launch { getPersonDetails(castId) }
            )
            if (state.value is ScreenState.LOADING)
                state.value = ScreenState.SUCCESS
        }
    }

    private suspend fun getPersonCredits(castId: Int) {
        when (val result = personCreditsUseCase(castId)) {
            is Result.Error -> if (result.exception is IOException)
                state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> personCredits.value = result.data
        }
    }

    private suspend fun getPersonDetails(castId: Int) {
        when (val result = personDetailsUseCase(castId)) {
            is Result.Error -> if (result.exception is IOException)
                state.value = ScreenState.FAILURE(result.errorMessage)

            is Result.Success -> personDetails.value = result.data 
        }
    }
}