package com.dev.playground.presentation.di

import com.dev.playground.presentation.preferences.SharedPreferencesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SharedPreferencesViewModel(
            getKakaoTokenUseCase = get(),
            setKakaoTokenUseCase = get()
        )
    }
}