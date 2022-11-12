package com.dev.playground.domain.repository

import com.dev.playground.domain.model.Auth

interface SharedPreferencesRepository {

    suspend fun getToken(): Auth?

    suspend fun setToken(token: Auth): Boolean

    suspend fun removeToken()

}