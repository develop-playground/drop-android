package com.dev.playground.domain.repository

interface SharedPreferencesRepository {

    suspend fun getKakaoToken(): Map<String, String?>

    suspend fun setKakaoToken(token: Map<String, String>)

}