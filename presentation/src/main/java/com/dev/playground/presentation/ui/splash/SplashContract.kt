package com.dev.playground.presentation.ui.splash

import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEvent
import com.dev.playground.presentation.model.base.UiState

class SplashContract {

    sealed interface State: UiState {
        object Idle: State
    }

    sealed interface Event: UiEvent

    sealed interface Effect: UiEffect {
        object RouteMainPage: Effect
    }

}