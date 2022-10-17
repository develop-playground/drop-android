package com.dev.playground.presentation.di

import com.dev.playground.presentation.util.preferences.SharedPreferencesManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localDataSourceModule = module {

    factory { SharedPreferencesManager(context = androidContext()) }

}