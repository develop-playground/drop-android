package com.dev.playground.presentation.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.usecase.login.GetKakaoTokenUseCase
import com.dev.playground.domain.usecase.login.SetKakaoTokenUseCase
import kotlinx.coroutines.launch

class SharedPreferencesViewModel(
    private val getKakaoTokenUseCase: GetKakaoTokenUseCase,
    private val setKakaoTokenUseCase: SetKakaoTokenUseCase,
) : ViewModel() {

    fun getKakaoToken() {

    }

    fun setKakaoToken(token: Map<String, String>) {
        viewModelScope.launch {
            setKakaoTokenUseCase.invoke(token)
        }

    }

}