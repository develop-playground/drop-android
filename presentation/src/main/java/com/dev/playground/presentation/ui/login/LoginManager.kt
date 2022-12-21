package com.dev.playground.presentation.ui.login

import com.dev.playground.domain.model.Token
import com.dev.playground.domain.model.type.LoginType

interface LoginManager {
    fun login(onSuccess: (Token, LoginType) -> Unit, onFailure: (Throwable) -> Unit)
    fun logout(callback: (error: Throwable?) -> Unit)
}