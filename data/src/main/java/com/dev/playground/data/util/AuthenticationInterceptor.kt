package com.dev.playground.data.util

import com.dev.playground.data.data_source.local.SharedPreferencesDataSource
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(
    private val preferencesManager: SharedPreferencesDataSource
) : Interceptor {

    companion object {
        const val KEY_AUTH = "Authorization"
        const val TYPE_AUTH = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {

        val accessToken =
            preferencesManager.getToken()?.accessToken ?: throw IllegalArgumentException()

        val newRequest = request().newBuilder()
            .addHeader(KEY_AUTH, "$TYPE_AUTH $accessToken")
            .build()

        proceed(newRequest)

    }

}