package com.dev.playground.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<S : BaseViewModel.UiState, E : BaseViewModel.UiEvent>(initialState: S) : ViewModel() {

    private val _currentState: MutableStateFlow<S> = MutableStateFlow(initialState)
    val currentState: StateFlow<S> = _currentState.asStateFlow()

    private val _eventFlow: MutableSharedFlow<E> = MutableSharedFlow()
    val eventFlow: SharedFlow<E> = _eventFlow.asSharedFlow()

    protected fun updateState(reducer: S.() -> S) {
        val newState = _currentState.value.reducer()
        _currentState.update { newState }
    }

    protected fun event(event: E) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    interface UiState
    interface UiEvent

}