package com.dev.playground.data.di

import com.dev.playground.data.repository.SharedPreferencesRepositoryImpl
import com.dev.playground.domain.repository.SharedPreferencesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(get()) }
}