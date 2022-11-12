package com.dev.playground.presentation.preferences

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.usecase.login.GetTokenUseCase
import com.dev.playground.domain.usecase.login.SetTokenUseCase
import com.dev.playground.presentation.preferences.SharedPreferencesViewModel.State.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SharedPreferencesViewModel(
    private val getTokenUseCase: GetTokenUseCase,
    private val setTokenUseCase: SetTokenUseCase
) : ViewModel() {

    sealed class State {
        data class Success(val data: Auth) : State()
        data class Failure(val error: Throwable) : State()
        object Loading : State()
    }

    private val _loginState: MutableStateFlow<State> = MutableStateFlow(Loading)
    val loginState: StateFlow<State> = _loginState.asStateFlow()

    fun getToken() {
        viewModelScope.launch {
            val result = getTokenUseCase.invoke()

            result
                .onSuccess {
                    _loginState.value = State.Success(it)
                }.onFailure {
                    _loginState.value = State.Failure(it)
                }
        }
    }

    fun setToken(token: Auth) {
        viewModelScope.launch {
            setTokenUseCase.invoke(token)
        }
    }

}