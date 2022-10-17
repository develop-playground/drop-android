package com.dev.playground.presentation.preferences

import androidx.lifecycle.ViewModel
import com.dev.playground.domain.repository.SharedPreferencesRepository

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