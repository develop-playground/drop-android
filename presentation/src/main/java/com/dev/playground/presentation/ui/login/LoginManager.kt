package com.dev.playground.presentation.ui.login

import android.content.Context
import com.dev.playground.domain.model.Token
import com.dev.playground.domain.model.type.LoginType

interface LoginManager {

    fun login(
        context: Context,
        onSuccess: (Token, LoginType) -> Unit,
        onFailure: (Throwable) -> Unit,
    )

    fun logout(callback: (error: Throwable?) -> Unit)
}