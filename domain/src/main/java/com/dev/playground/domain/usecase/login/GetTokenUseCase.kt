package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.repository.SharedPreferencesRepository
import com.dev.playground.domain.usecase.base.NonParamCoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetTokenUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    ioDispatcher: CoroutineDispatcher,
) : NonParamCoroutineUseCase<Auth>(ioDispatcher) {

    override suspend fun execute(): Auth {
        return sharedPreferencesRepository.getToken()
            ?: throw IllegalArgumentException("Access token has expired or is null")
    }

}