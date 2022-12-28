package com.dev.playground.presentation.di

import com.dev.playground.presentation.model.MemoryBundle
import com.dev.playground.presentation.ui.add.AddMemoryViewModel
import com.dev.playground.presentation.ui.feed.FeedViewModel
import com.dev.playground.presentation.ui.login.LoginViewModel
import com.dev.playground.presentation.ui.main.MainViewModel
import com.dev.playground.presentation.ui.modify.ModifyMemoryViewModel
import com.dev.playground.presentation.ui.setting.SettingViewModel
import com.dev.playground.presentation.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        SplashViewModel(getTokenUseCase = get())
    }

    viewModel {
        LoginViewModel(requestLoginUseCase = get())
    }
    viewModel {
        AddMemoryViewModel(
            uploadPhotoUseCase = get(),
            deletePhotoUseCase = get(),
            postMemoryUseCase = get(),
            getAddressUseCase = get()
        )
    }
    viewModel {
        FeedViewModel(
            getMemoryListUseCase = get(),
            deleteMemoryUseCase = get()
        )
    }
    viewModel {
        SettingViewModel(
            getUserEmailUseCase = get(),
            deleteUserUseCase = get(),
            getLoginTypeUseCase = get(),
            requestLogoutUseCase = get()
        )
    }
    viewModel { (bundle: MemoryBundle) ->
        ModifyMemoryViewModel(
            bundle = bundle,
            modifyMemoryUseCase = get()
        )
    }
    viewModel { MainViewModel() }
}