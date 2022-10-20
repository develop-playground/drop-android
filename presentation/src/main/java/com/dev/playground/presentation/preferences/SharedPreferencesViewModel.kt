package com.dev.playground.presentation.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.usecase.login.GetKakaoTokenUseCase
import com.dev.playground.domain.usecase.login.SetKakaoTokenUseCase
import com.dev.playground.presentation.preferences.SharedPreferencesViewModel.State.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedPreferencesViewModel(
    private val getKakaoTokenUseCase: GetKakaoTokenUseCase,
    private val setKakaoTokenUseCase: SetKakaoTokenUseCase
) : ViewModel() {

    sealed class State {
        data class Success(val data: Map<String, String?>) : State()
        data class Failure(val error: Throwable) : State()
        object Loading : State()
    }

    private val _loginState: MutableStateFlow<State> = MutableStateFlow(Loading)
    val loginState: StateFlow<State> = _loginState.asStateFlow()

    fun getKakaoToken() {
        viewModelScope.launch {
            val result = getKakaoTokenUseCase.invoke()

            result
                .onSuccess {
                    _loginState.value = State.Success(it)
                }.onFailure {
                    _loginState.value = State.Failure(it)
                }
        }
    }

    fun setKakaoToken(token: Map<String, String>) {
        viewModelScope.launch {
            setKakaoTokenUseCase.invoke(token)
        }
    }

}