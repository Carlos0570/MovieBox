package com.example.moviebox.core.presentation.screenStates

sealed class ScreenState {
    object LOADING : ScreenState()
    object SUCCESS : ScreenState()
    data class FAILURE(val message: String? = "Error") : ScreenState()
}