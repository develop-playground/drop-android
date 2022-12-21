package com.dev.playground.data.repository

import com.dev.playground.data.data_source.local.SharedPreferencesDataSource
import com.dev.playground.domain.model.Token
import com.dev.playground.domain.repository.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(
    private val sharedPreferencesDataSource: SharedPreferencesDataSource
) : SharedPreferencesRepository {

    override suspend fun getToken(): Token? {
        return sharedPreferencesDataSource.getToken()
    }

    override suspend fun setToken(token: Token) {
        return sharedPreferencesDataSource.setToken(token)
    }

    override suspend fun getLoginType(): String? {
        return sharedPreferencesDataSource.getLoginType()
    }

    override suspend fun setLoginType(type: String) {
        return sharedPreferencesDataSource.setLoginType(type)
    }

    override suspend fun clearAuth() {
        return sharedPreferencesDataSource.removeAuth()
    }

}