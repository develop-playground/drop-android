package com.dev.playground.data.di

import com.dev.playground.data.util.SharedPreferencesManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataSourceModule = module {
    factory { SharedPreferencesManager(context = androidContext()) }
}