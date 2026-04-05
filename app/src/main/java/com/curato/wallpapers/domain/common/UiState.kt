package com.curato.wallpapers.domain.common

/**
 * UDF state wrapper for all screens.
 * ViewModels emit UiState<T> via StateFlow — UI only reads, never writes.
 */
sealed class UiState<out T> {
    /** Initial state before any action is dispatched */
    object Idle : UiState<Nothing>()
    object Loading : UiState<Nothing>()
    data class Success<out T>(val data: T) : UiState<T>()
    data class Error(
        val message: String,
        val isRetryable: Boolean = true,
    ) : UiState<Nothing>()
}

fun <T> UiState<T>.isLoading() = this is UiState.Loading
fun <T> UiState<T>.isSuccess() = this is UiState.Success
fun <T> UiState<T>.dataOrNull(): T? = if (this is UiState.Success) data else null

/** Convert a domain Result to a UiState */
fun <T> Result<T>.toUiState(): UiState<T> = when (this) {
    is Result.Success -> UiState.Success(data)
    is Result.Error -> UiState.Error(message)
}
