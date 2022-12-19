package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoveKakaoTokenUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke() = withContext(dispatcher) {
        runCatching {
            sharedPreferencesRepository.removeToken()
        }
    }

}