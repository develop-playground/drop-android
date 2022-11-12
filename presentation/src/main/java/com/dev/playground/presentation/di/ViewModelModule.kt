package com.dev.playground.presentation.di

import com.dev.playground.presentation.login.LoginViewModel
import com.dev.playground.presentation.preferences.SharedPreferencesViewModel
import com.dev.playground.presentation.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SplashViewModel(getTokenUseCase = get())
    }

    viewModel {
        LoginViewModel(
            setTokenUseCase = get(),
            requestLoginUseCase = get()
        )
    }
}