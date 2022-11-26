package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.repository.AuthRepository
import com.dev.playground.domain.usecase.base.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher

class RequestLoginUseCase(
    private val authRepository: AuthRepository,
    ioDispatcher: CoroutineDispatcher,
) : CoroutineUseCase<String, Auth>(ioDispatcher) {

    override suspend fun execute(param: String): Auth {
        return authRepository.requestLogin(param)
    }

}