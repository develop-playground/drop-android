package com.dev.playground.domain.usecase.user.login

import com.dev.playground.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RequestLoginUseCase(private val authRepository: AuthRepository) {

    suspend operator fun invoke(params: String) = runCatching {
        authRepository.requestLogin(params)
    }

}