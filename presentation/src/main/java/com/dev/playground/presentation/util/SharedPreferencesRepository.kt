package com.dev.playground.presentation.util

interface SharedPreferencesRepository {

    fun getKakaoToken(): Map<String, String?>

    fun setKakaoToken(accessToken: String, refreshToken: String)

}