package com.example.moviebox.core.network

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

suspend fun <T> safeAPICall(function: suspend () -> T): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.Success(function())
        } catch (e: Exception) {
            Result.Error(e, e.message)
        }
    }
}