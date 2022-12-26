package com.dev.playground.data.util

import com.dev.playground.data.data_source.local.SharedPreferencesDataSource
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthenticationInterceptor(
    private val preferencesManager: SharedPreferencesDataSource
) : Interceptor {

    companion object {
        const val KEY_AUTH = "Authorization"
        const val TYPE_AUTH = "Bearer"
    }

    override fun intercept(chain: Interceptor.Chain): Response = with(chain) {
        val accessToken = preferencesManager.getToken()?.accessToken ?: throw IOException()
        val superKey = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBQ0NFU1MiLCJhdWQiOiJhZG1pbkBlbWFpbC5jb20iLCJpYXQiOjE2Njk4NTMxMjYsImV4cCI6MzMyMDU4NTMxMjZ9.lYwBcQBRr12EpxoE31i0AzFWUWk2Zeo4i6j7Psocl5jbLxBZJMgH-eLCk14JvKpZTzFkoz2TjYPXJigRjW62vg"
        val newRequest = request().newBuilder()
            .addHeader(KEY_AUTH, "$TYPE_AUTH $superKey")
            .build()

        proceed(newRequest)

    }

}