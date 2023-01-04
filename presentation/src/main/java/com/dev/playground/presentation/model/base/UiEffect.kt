package com.dev.playground.presentation.model.base

import com.dev.playground.presentation.model.MemoryBundle

interface UiEffect {
    sealed interface NavigationEffect : UiEffect {
        object RouteMainPage : NavigationEffect
        data class RouteLoginPage(val force: Boolean = false) : NavigationEffect
        data class RouteModifyPage(val bundle: MemoryBundle) : NavigationEffect
        object RouteAddPage : NavigationEffect
    }
}