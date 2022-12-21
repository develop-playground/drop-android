package com.dev.playground.domain.usecase.user.login

import com.dev.playground.domain.model.Token
import com.dev.playground.domain.repository.AuthRepository
import com.dev.playground.domain.repository.SharedPreferencesRepository

class RequestLoginUseCase(
    private val authRepository: AuthRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) {

    suspend operator fun invoke(params: String, snsToken: Token) = runCatching {
        sharedPreferencesRepository.setToken(snsToken)
    }.mapCatching {
        authRepository.requestLogin(params)
    }.mapCatching {
        sharedPreferencesRepository.setToken(it)
        sharedPreferencesRepository.setLoginType(params)
    }.onFailure {
        sharedPreferencesRepository.clearAuth()
    }

}