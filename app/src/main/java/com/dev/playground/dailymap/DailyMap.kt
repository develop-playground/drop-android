package com.dev.playground.dailymap

import android.app.Application
import com.dev.playground.presentation.di.localDataSourceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DailyMap: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                localDataSourceModule
            )
            androidContext(this@DailyMap)
        }
    }

}