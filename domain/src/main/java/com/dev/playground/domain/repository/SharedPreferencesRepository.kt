package com.dev.playground.domain.repository

interface SharedPreferencesRepository {

    fun getKakaoToken(): Map<String, String?>

    fun setKakaoToken(accessToken: String, refreshToken: String)

}