package com.dev.playground.presentation.model.base

import androidx.annotation.StringRes
import com.dev.playground.presentation.R

interface UiEffect {
    abstract class AuthEffect(@StringRes val message: Int) : UiEffect
    object RouteLoginPage: AuthEffect(R.string.please_re_log_in)
}