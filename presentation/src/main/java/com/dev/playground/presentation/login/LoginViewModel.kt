package com.dev.playground.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.usecase.login.RequestLoginUseCase
import com.dev.playground.presentation.login.LoginViewModel.State.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val requestLoginUseCase: RequestLoginUseCase
) : ViewModel() {

    sealed class State {
        data class Success(val data: Auth) : State()
        data class Failure(val error: Throwable) : State()
        object Loading : State()
    }

    private val _isSignIn: MutableStateFlow<State> = MutableStateFlow(Loading)
    val isSignIn: StateFlow<State> = _isSignIn.asStateFlow()

    fun login(memberType: String) {
        viewModelScope.launch {
            val result = requestLoginUseCase.invoke(memberType)

            result
                .onSuccess {
                    _isSignIn.value = Success(it)
                }.onFailure {
                    _isSignIn.value = Failure(it)
                }
        }
    }
}