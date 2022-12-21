package com.dev.playground.domain.usecase.user.login

import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class SetTokenUseCase(private val sharedPreferencesRepository: SharedPreferencesRepository) {

    suspend operator fun invoke(params: Auth) = runCatching {
        sharedPreferencesRepository.setToken(params)
    }

}