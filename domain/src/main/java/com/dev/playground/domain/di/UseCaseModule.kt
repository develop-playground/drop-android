package com.dev.playground.domain.di

import com.dev.playground.domain.usecase.login.GetKakaoTokenUseCase
import com.dev.playground.domain.usecase.login.RemoveKakaoTokenUseCase
import com.dev.playground.domain.usecase.login.SetKakaoTokenUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetKakaoTokenUseCase(get(), get(named(IO))) }
    factory { SetKakaoTokenUseCase(get(), get(named(IO))) }
    factory { RemoveKakaoTokenUseCase(get(), get(named(IO))) }
}