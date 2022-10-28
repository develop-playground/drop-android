package com.dev.playground.data.util

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor(
    private val preferencesManager: SharedPreferencesManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val accessToken =
            "Bearer " + preferencesManager.getKakaoToken()["access_token"].toString()

        val newRequest = request().newBuilder()
            .addHeader("Authorization", accessToken)
            .build()

        proceed(newRequest)
    }

}