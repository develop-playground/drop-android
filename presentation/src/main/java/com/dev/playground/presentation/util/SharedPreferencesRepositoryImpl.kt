package com.dev.playground.presentation.util

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