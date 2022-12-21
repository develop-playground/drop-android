package com.dev.playground.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dev.playground.domain.model.Token
import com.dev.playground.domain.usecase.user.login.GetTokenUseCase
import com.dev.playground.presentation.ui.splash.SplashViewModel.SplashState.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val getTokenUseCase: GetTokenUseCase
) : ViewModel() {

    private val _splashState: MutableStateFlow<SplashState> = MutableStateFlow(
        Loading
    )
    val splashState: StateFlow<SplashState> = _splashState.asStateFlow()

    fun checkLoginStatus() {
        viewModelScope.launch {
            val result = getTokenUseCase.invoke()

            result
                .onSuccess { auth ->
                    _splashState.update { Success(auth) }
                }.onFailure { throwable ->
                    _splashState.update { Failure(throwable) }
                }
        }
    }

    sealed class SplashState {
        data class Success(val data: Token) : SplashState()
        data class Failure(val error: Throwable) : SplashState()
        object Loading : SplashState()
    }

}