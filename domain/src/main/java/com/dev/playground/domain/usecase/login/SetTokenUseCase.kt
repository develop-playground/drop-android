package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SetTokenUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(param: Auth) = withContext(ioDispatcher) {
        runCatching {
            sharedPreferencesRepository.setToken(param)
        }
    }

}