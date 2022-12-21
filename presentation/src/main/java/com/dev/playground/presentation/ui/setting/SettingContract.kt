package com.dev.playground.presentation.ui.setting

import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEvent
import com.dev.playground.presentation.model.base.UiState

class SettingContract {

    sealed interface State: UiState {
        object Idle: State
        data class Success(val email: String): State
    }

    sealed interface Event: UiEvent

    sealed interface Effect: UiEffect {
        object FailLoadEmail: Effect
    }

}