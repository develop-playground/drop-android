package com.dev.playground.data.repository

import com.dev.playground.data.data_source.local.SharedPreferencesDataSource
import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.repository.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) : SharedPreferencesRepository {

    override suspend fun getToken(): Auth? {
        return sharedPreferencesDataSource.getToken()
    }

    override suspend fun setToken(token: Auth) {
        return sharedPreferencesDataSource.setToken(token)
    }

    override suspend fun removeToken() {
        return sharedPreferencesDataSource.removeToken()
    }

}