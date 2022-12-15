package com.dev.playground.data.di

import com.dev.playground.data.data_source.remote.FirebaseDataSource
import org.koin.dsl.module

val remoteDataSourceModule = module {
    single { FirebaseDataSource() }
}