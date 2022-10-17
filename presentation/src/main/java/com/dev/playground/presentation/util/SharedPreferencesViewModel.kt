package com.dev.playground.presentation.util

import androidx.lifecycle.ViewModel

class SharedPreferencesViewModel(
    private val sharedPreferencesRepository: SharedPreferencesRepository
) : ViewModel() {

    fun getKakaoToken(): Map<String, String?> {
        return sharedPreferencesRepository.getKakaoToken()
    }

    fun setKakaoToken(accessToken: String, refreshToken: String) {
        sharedPreferencesRepository.setKakaoToken(accessToken, refreshToken)
    }

}