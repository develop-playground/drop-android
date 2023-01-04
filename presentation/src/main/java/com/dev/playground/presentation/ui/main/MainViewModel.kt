package com.dev.playground.presentation.ui.main

import com.dev.playground.presentation.base.BaseViewModel
import com.dev.playground.presentation.model.base.UiEffect
import com.dev.playground.presentation.model.base.UiEffect.NavigationEffect.*
import com.dev.playground.presentation.model.base.UiEvent
import com.dev.playground.presentation.model.base.UiEvent.NavigationEvent.*
import com.dev.playground.presentation.ui.main.MainContract.State
import com.dev.playground.presentation.ui.main.MainContract.State.Idle

class MainViewModel : BaseViewModel<State, UiEvent, UiEffect>(Idle) {

    override fun handleEvent(event: UiEvent) {
        when (event) {
            is RequestRouteLogin -> setEffect {
                RouteLoginPage(event.force)
            }
            is RequestRouteModify -> setEffect {
                RouteModifyPage(event.bundle)
            }
            RequestRouteAdd -> setEffect {
                RouteAddPage
            }
        }
    }

}