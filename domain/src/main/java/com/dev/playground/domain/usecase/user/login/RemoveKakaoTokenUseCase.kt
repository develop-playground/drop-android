package com.dev.playground.domain.usecase.user.login

import com.dev.playground.domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoveKakaoTokenUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    suspend operator fun invoke() = runCatching {
        sharedPreferencesRepository.removeToken()
    }

}