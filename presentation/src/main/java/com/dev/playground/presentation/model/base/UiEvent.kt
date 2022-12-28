package com.dev.playground.presentation.model.base

import com.dev.playground.presentation.model.MemoryBundle

interface UiEvent {
    sealed interface NavigationEvent: UiEvent {
        data class RequestRouteLogin(val force: Boolean = true): NavigationEvent
        data class RequestRouteModify(val bundle: MemoryBundle): NavigationEvent
        object RequestRouteAdd: NavigationEvent
    }
}