package com.dev.playground.data.di

import com.dev.playground.data.data_source.local.SharedPreferencesDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataSourceModule = module {
    factory { SharedPreferencesDataSource(context = androidContext()) }
}