package com.dev.playground.presentation.util.preferences

interface SharedPreferencesRepository {

    fun getKakaoToken(): Map<String, String?>

    fun setKakaoToken(accessToken: String, refreshToken: String)

}