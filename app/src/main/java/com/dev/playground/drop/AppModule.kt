package com.dev.playground.drop

import com.dev.playground.data.di.BASE_URL_KEY
import com.dev.playground.data.di.X_NCP_APIGW_API_KEY
import com.dev.playground.data.di.X_NCP_APIGW_API_KEY_ID
import com.dev.playground.presentation.R
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    single(qualifier = named(BASE_URL_KEY)) { BuildConfig.BASE_URL }
    single(qualifier = named(X_NCP_APIGW_API_KEY_ID)) { androidContext().resources.getString(R.string.naver_client_id) }
    single(qualifier = named(X_NCP_APIGW_API_KEY)) { androidContext().resources.getString(R.string.naver_client_secret) }
}