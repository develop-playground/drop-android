package com.dev.playground.domain.usecase.user

import com.dev.playground.domain.repository.AuthRepository
import com.dev.playground.domain.repository.SharedPreferencesRepository

class DeleteUserUseCase(
    private val authRepository: AuthRepository,
    private val sharedPreferencesRepository: SharedPreferencesRepository,
) {

    suspend operator fun invoke() = runCatching {
        authRepository.deleteUser()
    }.mapCatching {
        sharedPreferencesRepository.clearAuth()
    }

}