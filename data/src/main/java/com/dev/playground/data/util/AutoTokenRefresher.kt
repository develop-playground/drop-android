package com.dev.playground.data.util

import com.dev.playground.data.data_source.local.SharedPreferencesDataSource
import com.dev.playground.data.data_source.remote.AuthService
import com.dev.playground.domain.exception.NotLoggedInException
import retrofit2.HttpException
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class AutoTokenRefresher(
    private val sharedPreferencesDataSource: SharedPreferencesDataSource,
    private val service: AuthService,
) {

    suspend fun <T> authHandle(action: suspend () -> T): T {
        return try {
            action.invoke()
        } catch (e: HttpException) {
            if (e.isUnAuthorized()) {
                refreshToken()
            }
            action.invoke()
        }
    }

    private suspend fun refreshToken() {
        try {
            val tokenData = service.refreshAccessToken()
            sharedPreferencesDataSource.setAccessToken(tokenData.accessToken)
        } catch (e: HttpException) {
            if (e.isUnAuthorized()) {
                sharedPreferencesDataSource.removeAuth()
                throw NotLoggedInException()
            } else {
                throw e
            }
        }
    }

    private fun HttpException.isUnAuthorized(): Boolean = code() == HTTP_UNAUTHORIZED

}