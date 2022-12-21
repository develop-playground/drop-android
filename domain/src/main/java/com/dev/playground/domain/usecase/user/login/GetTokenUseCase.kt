package com.dev.playground.domain.usecase.user.login

import com.dev.playground.domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetTokenUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    suspend operator fun invoke() = runCatching {
        sharedPreferencesRepository.getToken()
            ?: throw IllegalArgumentException("Access token has expired or is null")
    }

}