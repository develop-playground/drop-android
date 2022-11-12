package com.dev.playground.domain.di

import com.dev.playground.domain.usecase.login.GetTokenUseCase
import com.dev.playground.domain.usecase.login.RemoveKakaoTokenUseCase
import com.dev.playground.domain.usecase.login.RequestLoginUseCase
import com.dev.playground.domain.usecase.login.SetTokenUseCase
import org.koin.core.qualifier.named
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetTokenUseCase(get(), get(named(IO))) }
    factory { SetTokenUseCase(get(), get(named(IO))) }
    factory { RemoveKakaoTokenUseCase(get(), get(named(IO))) }
    factory { RequestLoginUseCase(get(), get(named(IO))) }
}