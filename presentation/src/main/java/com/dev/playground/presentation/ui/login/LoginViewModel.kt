package com.dev.playground.presentation.ui.login

import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.model.Token
import com.dev.playground.domain.model.type.LoginType
import com.dev.playground.domain.usecase.user.login.RequestLoginUseCase
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEffect.NavigationEffect.RouteMainPage
import com.dev.playground.presentation.ui.login.LoginContract.Effect.ShowFailRequestLoginToast
import com.dev.playground.presentation.ui.login.LoginContract.Event
import com.dev.playground.presentation.ui.login.LoginContract.Event.OnSnsLoginComplete
import com.dev.playground.presentation.ui.login.LoginContract.State
import com.dev.playground.presentation.ui.login.LoginContract.State.Initialize
import kotlinx.coroutines.launch

class LoginViewModel(
    private val requestLoginUseCase: RequestLoginUseCase,
) : BaseViewModel<State, Event, UiEffect>(Initialize) {

    private fun requestLogin(token: Token, loginType: LoginType) {
        viewModelScope.launch {
            requestLoginUseCase.invoke(
                params = loginType.name,
                snsToken = token
            ).onSuccess {
                setEffect {
                    RouteMainPage
                }
            }.onFailure {
                setEffect {
                    ShowFailRequestLoginToast
                }
            }
        }
    }

    override fun handleEvent(event: Event) {
        (event as? OnSnsLoginComplete)?.let {
            requestLogin(it.token, it.type)
        }
    }

}