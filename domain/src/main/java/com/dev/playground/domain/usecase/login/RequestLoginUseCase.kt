package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.repository.AuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RequestLoginUseCase(
    private val authRepository: AuthRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(param: String) = withContext(ioDispatcher) {
        runCatching {
            authRepository.requestLogin(param)
        }
    }

}