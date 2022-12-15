package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SetTokenUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(params: Auth) = withContext(dispatcher) {
        runCatching {
            sharedPreferencesRepository.setToken(param)
        }
    }

}