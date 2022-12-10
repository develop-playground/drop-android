package com.dev.playground.presentation.di

import com.dev.playground.presentation.ui.add.AddMemoryViewModel
import com.dev.playground.presentation.ui.feed.FeedViewModel
import com.dev.playground.presentation.ui.login.LoginViewModel
import com.dev.playground.presentation.ui.splash.SplashViewModel
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
    viewModel { AddMemoryViewModel() }
    viewModel { FeedViewModel(getMemoryListUseCase = get()) }
}