package com.dev.playground.presentation.ui.splash

import com.dev.playground.presentation.model.base.UiState

class SplashContract {

    sealed interface State : UiState {
        object Idle : State
    }

}