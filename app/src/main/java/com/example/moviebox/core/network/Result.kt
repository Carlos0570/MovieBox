package com.example.moviebox.core.network

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val exception: Exception,
        val errorMessage: String? = "Error",
    ) : Result<Nothing>()
}