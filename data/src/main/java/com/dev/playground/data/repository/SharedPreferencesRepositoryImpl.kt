package com.dev.playground.data.repository

import com.dev.playground.data.util.SharedPreferencesManager
import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.repository.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(
    private val sharedPreferencesManager: SharedPreferencesManager
) : SharedPreferencesRepository {

    override suspend fun getToken(): Auth? {
        return sharedPreferencesManager.getToken()
    }

    override suspend fun setToken(token: Auth): Boolean {
        return sharedPreferencesManager.setToken(token)
    }

    override suspend fun removeToken() {
        return sharedPreferencesManager.removeToken()
    }

}