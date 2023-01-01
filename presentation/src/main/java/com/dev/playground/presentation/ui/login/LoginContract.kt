package com.dev.playground.presentation.ui.login

import com.dev.playground.domain.model.Token
import com.dev.playground.domain.model.type.LoginType
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEvent
import com.dev.playground.presentation.model.base.UiState

class LoginContract {
    sealed interface State : UiState {
        object Initialize : State
    }

    sealed interface Event : UiEvent {
        data class OnSnsLoginComplete(val token: Token, val type: LoginType) : Event
    }

    sealed interface Effect : UiEffect {
        object ShowFailRequestLoginToast : Effect
    }
}