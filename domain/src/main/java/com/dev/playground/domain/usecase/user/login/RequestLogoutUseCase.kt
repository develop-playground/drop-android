package com.dev.playground.domain.usecase.user.login

import com.dev.playground.domain.repository.AuthRepository
import com.dev.playground.domain.repository.SharedPreferencesRepository

class RequestLogoutUseCase(
    private val authRepository: AuthRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository
) {

    suspend operator fun invoke() = runCatching {
        authRepository.postLogout()
    }.mapCatching {
        sharedPreferencesRepository.clearAuth()
    }

}