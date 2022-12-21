package com.dev.playground.domain.repository

import com.dev.playground.domain.model.Token

interface SharedPreferencesRepository {

    suspend fun getToken(): Token?

    suspend fun setToken(token: Token)

    suspend fun setLoginType(type: String)

    suspend fun getLoginType(): String?

    suspend fun clearAuth()

}