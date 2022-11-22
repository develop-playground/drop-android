package com.dev.playground.presentation.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T : BaseViewModel.Event> : ViewModel() {

    private val _eventFlow: MutableSharedFlow<T> = MutableSharedFlow()
    val eventFlow: SharedFlow<T> = _eventFlow.asSharedFlow()

    protected fun event(event: T) {
        viewModelScope.launch {
            _eventFlow.emit(event)
        }
    }

    interface Event

}