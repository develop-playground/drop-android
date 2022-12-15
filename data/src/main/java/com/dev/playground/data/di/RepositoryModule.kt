package com.dev.playground.data.di

import com.dev.playground.data.repository.AuthRepositoryImpl
import com.dev.playground.data.repository.MemoryRepositoryImpl
import com.dev.playground.data.repository.PhotoRepositoryImpl
import com.dev.playground.data.repository.SharedPreferencesRepositoryImpl
import com.dev.playground.domain.repository.AuthRepository
import com.dev.playground.domain.repository.MemoryRepository
import com.dev.playground.domain.repository.PhotoRepository
import com.dev.playground.domain.repository.SharedPreferencesRepository
import org.koin.dsl.module

val repositoryModule = module {
    single<SharedPreferencesRepository> { SharedPreferencesRepositoryImpl(get()) }
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<MemoryRepository> { MemoryRepositoryImpl(get()) }
    single<PhotoRepository> { PhotoRepositoryImpl(get()) }
}