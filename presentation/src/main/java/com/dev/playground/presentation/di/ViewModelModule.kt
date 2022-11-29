package com.dev.playground.presentation.di

import com.dev.playground.presentation.ui.login.LoginViewModel
import com.dev.playground.presentation.ui.splash.SplashViewModel
import com.dev.playground.presentation.ui.feed.FeedViewModel
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
    viewModel { FeedViewModel(getMemoryListUseCase = get()) }
}