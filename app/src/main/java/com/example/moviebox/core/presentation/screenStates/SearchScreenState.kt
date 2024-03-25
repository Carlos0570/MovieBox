package com.example.moviebox.core.presentation.screenStates

sealed class SearchScreenState {
    object START_SCREEN : SearchScreenState()
    object LOADING : SearchScreenState()
    object SUCCESS : SearchScreenState()
    object NOT_FOUND : SearchScreenState()
    data class FAILURE(val message: String? = "Error") : SearchScreenState()
}