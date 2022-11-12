package com.dev.playground.drop

import android.app.Application
import com.dev.playground.data.di.localDataSourceModule
import com.dev.playground.data.di.networkModule
import com.dev.playground.data.di.repositoryModule
import com.dev.playground.domain.di.dispatcherModule
import com.dev.playground.domain.di.useCaseModule
import com.dev.playground.presentation.R
import com.dev.playground.presentation.di.viewModelModule
import com.kakao.sdk.common.KakaoSdk
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DropApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, getString(R.string.kakao_native_app_key))

        startKoin {
            modules(
                appModule,
                viewModelModule,
                dispatcherModule,
                useCaseModule,
                networkModule,
                repositoryModule,
                localDataSourceModule,
            )
            androidContext(this@DropApplication)
        }
    }
}