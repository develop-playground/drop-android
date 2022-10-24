package com.dev.playground.drop

import android.app.Application
import com.dev.playground.data.di.localDataSourceModule
import com.dev.playground.data.di.repositoryModule
import com.dev.playground.domain.di.dispatcherModule
import com.dev.playground.domain.di.useCaseModule
import com.dev.playground.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DropApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                viewModelModule,
                dispatcherModule,
                useCaseModule,
                repositoryModule,
                localDataSourceModule,
            )
            androidContext(this@DropApplication)
        }
    }
}