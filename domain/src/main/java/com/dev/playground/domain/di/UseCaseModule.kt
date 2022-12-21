package com.dev.playground.domain.di

import com.dev.playground.domain.usecase.location.GetAddressUseCase
import com.dev.playground.domain.usecase.memory.DeleteMemoryUseCase
import com.dev.playground.domain.usecase.memory.GetMemoryListUseCase
import com.dev.playground.domain.usecase.memory.PostMemoryUseCase
import com.dev.playground.domain.usecase.photo.DeletePhotoUseCase
import com.dev.playground.domain.usecase.photo.UploadPhotoUseCase
import com.dev.playground.domain.usecase.user.GetUserUseCase
import com.dev.playground.domain.usecase.user.login.GetTokenUseCase
import com.dev.playground.domain.usecase.user.login.RemoveKakaoTokenUseCase
import com.dev.playground.domain.usecase.user.login.RequestLoginUseCase
import com.dev.playground.domain.usecase.user.login.SetTokenUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetTokenUseCase(get()) }

    factory { GetUserUseCase(get()) }
    factory {
        RequestLoginUseCase(
            authRepository = get(),
            sharedPreferencesRepository = get()
        )
    }
    factory { GetLoginTypeUseCase(get()) }

    factory { GetMemoryListUseCase(get()) }
    factory { PostMemoryUseCase(get()) }
    factory { DeleteMemoryUseCase(get()) }

    factory { UploadPhotoUseCase(get(), get(named(IO))) }
    factory { DeletePhotoUseCase(get(), get(named(IO))) }

    factory { GetAddressUseCase(get()) }
}