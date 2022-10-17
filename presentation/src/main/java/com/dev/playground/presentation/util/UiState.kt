package com.dev.playground.presentation.util

sealed class UiState<out T> {

    data class Success<out T>(val data: T?) : UiState<T>()

    data class Failure(val exception: Throwable): UiState<Nothing>()

    data class Loading(val loading: Boolean = false): UiState<Nothing>()

}