package com.dev.playground.data.repository

import com.dev.playground.data.util.SharedPreferencesManager
import com.dev.playground.domain.repository.SharedPreferencesRepository

class SharedPreferencesRepositoryImpl(
    private val sharedPreferencesManager: SharedPreferencesManager
) : SharedPreferencesRepository {

    override fun getKakaoToken(): Map<String, String?> {
        return sharedPreferencesManager.getKakaoToken()
    }

    override fun setKakaoToken(accessToken: String, refreshToken: String) {
        return sharedPreferencesManager.setKakaoToken(accessToken, refreshToken)
    }

}