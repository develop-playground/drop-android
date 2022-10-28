package com.dev.playground.drop

import com.dev.playground.data.di.BASE_URL_KEY
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(qualifier = named(BASE_URL_KEY)) { BuildConfig.BASE_URL}
}