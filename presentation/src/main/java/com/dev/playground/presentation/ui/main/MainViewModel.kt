package com.dev.playground.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _routeLoginPageEffect: Channel<Unit> = Channel()
    val routeLoginPageEffect = _routeLoginPageEffect.receiveAsFlow()

    fun routeLoginPage() {
        viewModelScope.launch {
            _routeLoginPageEffect.send(Unit)
        }
    }

}