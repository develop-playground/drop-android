package com.dev.playground.data.util

import com.dev.playground.data.data_source.local.SharedPreferencesDataSource
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(
    private val preferencesManager: SharedPreferencesDataSource,
) : Interceptor {

    companion object {
        const val KEY_AUTH = "Authorization"
        const val TYPE_AUTH = "Bearer"
        const val URL_LOGOUT = "logout"
        const val URL_USER = "user"
    }

    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val token = preferencesManager.getToken() ?: throw IOException()
        val headerValue = if (
            listOf(URL_USER, URL_LOGOUT).any { request().url.encodedPath.contains(it) }
        ) {
            token.refreshToken
        } else {
            token.accessToken
        }

        val newRequest = request().newBuilder()
            .addHeader(KEY_AUTH, "$TYPE_AUTH $headerValue")
            .build()

        proceed(newRequest)
    }

}