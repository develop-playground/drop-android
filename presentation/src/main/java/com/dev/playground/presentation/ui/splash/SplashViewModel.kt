package com.dev.playground.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.usecase.user.login.GetTokenUseCase
import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEffect.NavigationEffect.RouteLoginPage
import com.dev.playground.presentation.model.base.UiEffect.NavigationEffect.RouteMainPage
import com.dev.playground.presentation.model.base.UiEvent
import com.dev.playground.presentation.ui.splash.SplashContract.State
import com.dev.playground.presentation.ui.splash.SplashContract.State.Idle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getTokenUseCase: GetTokenUseCase,
) : BaseViewModel<State, UiEvent, UiEffect>(Idle) {

    init {
        checkHasToken()
    }

    private fun checkHasToken() {
        viewModelScope.launch {
            val result = getTokenUseCase.invoke()

            result.onSuccess {
                setEffect {
                    RouteMainPage
                }
            }.onFailure {
                setEffect {
                    RouteLoginPage()
                }
            }
        }
    }

    override fun handleEvent(event: UiEvent) {
        // no-op
    }

}