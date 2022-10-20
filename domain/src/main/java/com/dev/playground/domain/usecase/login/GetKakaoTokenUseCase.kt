package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.repository.SharedPreferencesRepository
import com.dev.playground.domain.usecase.base.NonParamCoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetKakaoTokenUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    ioDispatcher: CoroutineDispatcher
) : NonParamCoroutineUseCase<Map<String, String?>>(ioDispatcher) {

    override suspend fun execute(): Map<String, String?> {
        val token = sharedPreferencesRepository.getKakaoToken()

        return if (token["access_token"].isNullOrEmpty()) {
            throw IllegalArgumentException("Access token has expired or is null")
        } else {
            token
        }
    }

}