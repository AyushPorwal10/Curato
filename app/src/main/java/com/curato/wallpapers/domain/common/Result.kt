package com.curato.wallpapers.domain.common

import androidx.compose.ui.geometry.Rect

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val exception: Throwable,
        val message: String = exception.message ?: "Unknown error",
    ) : Result<Nothing>()
    object Loading : Result<Nothing>()
    object Empty : Result<Nothing>()
}

inline fun <T> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}


inline fun <T> Result<T>.onError(action: (Throwable, String) -> Unit): Result<T> {
    if (this is Result.Error) action(exception, message)
    return this
}

inline fun <T, R> Result<T>.map(transform: (T) -> R): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(data))
    is Result.Error -> this
    is Result.Loading -> Result.Loading
    is Result.Empty -> Result.Empty
}

inline fun <T> Result<T>.getOrNull(): T? = if (this is Result.Success) data else null

inline fun <T> Result<T>.getOrDefault(default: T): T = if (this is Result.Success) data else default

// Wraps a suspend block, catching all exceptions into Result.Error
suspend inline fun <T> safeCall(crossinline block: suspend () -> T): Result<T> = try {
    Result.Success(block())
} catch (e: Exception) {
    Result.Error(e)
}

