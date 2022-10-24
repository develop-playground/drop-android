package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.repository.SharedPreferencesRepository
import com.dev.playground.domain.usecase.base.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SetKakaoTokenUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    ioDispatcher: CoroutineDispatcher
) : CoroutineUseCase<Map<String, String>, Unit>(ioDispatcher) {

    override suspend fun execute(param: Map<String, String>) {
        sharedPreferencesRepository.setKakaoToken(param)
    }

}