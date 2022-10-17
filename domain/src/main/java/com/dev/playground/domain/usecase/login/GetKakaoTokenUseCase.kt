package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.repository.SharedPreferencesRepository
import com.dev.playground.domain.usecase.base.NonCoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher

class GetKakaoTokenUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    ioDispatcher: CoroutineDispatcher
) : NonCoroutineUseCase<Map<String, String?>>(ioDispatcher) {

    override suspend fun execute(): Map<String, String?> {
        return sharedPreferencesRepository.getKakaoToken()
    }

}