package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetTokenUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val ioDispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke() = withContext(ioDispatcher) {
        runCatching {
            sharedPreferencesRepository.getToken()
                ?: throw IllegalArgumentException("Access token has expired or is null")
        }
    }

}