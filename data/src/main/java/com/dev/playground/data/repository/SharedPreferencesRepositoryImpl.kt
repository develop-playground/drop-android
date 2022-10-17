package com.dev.playground.data.repository

import com.dev.playground.data.util.SharedPreferencesManager
import com.dev.playground.domain.repository.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(
    private val sharedPreferencesManager: SharedPreferencesManager
) : SharedPreferencesRepository {

    override suspend fun getKakaoToken(): Map<String, String?> {
        return sharedPreferencesManager.getKakaoToken()
    }

    override suspend fun setKakaoToken(token: Map<String, String>) {
        return sharedPreferencesManager.setKakaoToken(token)
    }

}