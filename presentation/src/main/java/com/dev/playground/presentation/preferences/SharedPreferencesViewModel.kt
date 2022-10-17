package com.dev.playground.presentation.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.usecase.login.GetKakaoTokenUseCase
import com.dev.playground.domain.usecase.login.SetKakaoTokenUseCase
import com.dev.playground.presentation.util.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SharedPreferencesViewModel(
    private val getKakaoTokenUseCase: GetKakaoTokenUseCase,
    private val setKakaoTokenUseCase: SetKakaoTokenUseCase,
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<Map<String, String?>>>(
        UiState.Loading(false)
    )
    val loginState = _loginState.asStateFlow()

    fun getKakaoToken() {
        viewModelScope.launch {
            val result = getKakaoTokenUseCase.invoke()

            result
                .onSuccess { token ->
                    _loginState.value = UiState.Success(token)
                }.onFailure {
                    _loginState.value = UiState.Failure(it)
                }
        }
    }

    fun setKakaoToken(token: Map<String, String>) {

        viewModelScope.launch {
            setKakaoTokenUseCase.invoke(token)
        }

    }

}