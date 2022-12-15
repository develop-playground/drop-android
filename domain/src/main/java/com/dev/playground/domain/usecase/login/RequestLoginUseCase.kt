package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RequestLoginUseCase(
    private val authRepository: AuthRepository,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(params: String) = withContext(dispatcher) {
        runCatching {
            authRepository.requestLogin(param)
        }
    }

}