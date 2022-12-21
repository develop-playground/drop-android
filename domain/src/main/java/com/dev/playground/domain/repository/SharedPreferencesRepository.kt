package com.dev.playground.domain.repository

import com.dev.playground.domain.model.Auth

interface SharedPreferencesRepository {

    suspend fun getToken(): Auth?

    suspend fun setToken(token: Auth)

    suspend fun removeToken()
    suspend fun setLoginType(type: String)

    suspend fun getLoginType(): String?

}