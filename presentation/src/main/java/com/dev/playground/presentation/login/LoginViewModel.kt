package com.dev.playground.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.model.type.TokenType
import com.dev.playground.domain.usecase.login.RequestLoginUseCase
import com.dev.playground.domain.usecase.login.SetTokenUseCase
import com.dev.playground.presentation.login.LoginViewModel.LoginEvent.SaveDropToken
import com.dev.playground.presentation.login.LoginViewModel.LoginEvent.SaveSNSToken
import com.dev.playground.presentation.login.LoginViewModel.LoginState.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class LoginViewModel(
    private val setTokenUseCase: SetTokenUseCase,
    private val requestLoginUseCase: RequestLoginUseCase
) : ViewModel() {

    private val _loginState: MutableStateFlow<LoginState> = MutableStateFlow(Loading)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _loginEvent: MutableSharedFlow<LoginEvent> = MutableSharedFlow()
    val loginEvent: SharedFlow<LoginEvent> = _loginEvent.asSharedFlow()

    fun requestLogin(memberType: String) {
        viewModelScope.launch {
            val result = requestLoginUseCase.invoke(memberType)

            result.onSuccess {
                _loginState.value = Success(it)
            }.onFailure {
                _loginState.value = Failure(it)
            }
        }
    }

    fun storeToken(
        accessToken: String,
        refreshToken: String,
        type: TokenType
    ) {
        viewModelScope.launch {
            val result = setTokenUseCase.invoke(Auth(accessToken, refreshToken))

            result
                .onSuccess {
                    val event = when (type) {
                        TokenType.SNS -> SaveSNSToken
                        else -> SaveDropToken
                    }
                    _loginEvent.emit(event)
                }.onFailure {

                }
        }
    }

    sealed class LoginState {
        data class Success(val data: Auth) : LoginState()
        data class Failure(val error: Throwable) : LoginState()
        object Loading : LoginState()
    }

    sealed interface LoginEvent {
        object SaveSNSToken : LoginEvent
        object SaveDropToken : LoginEvent
    }
}