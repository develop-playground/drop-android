package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.repository.SharedPreferencesRepository
import com.dev.playground.domain.usecase.base.NonCoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher

class RemoveKakaoTokenUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    ioDispatcher: CoroutineDispatcher
) : NonCoroutineUseCase<Unit>(ioDispatcher) {

    override suspend fun execute() {
        return sharedPreferencesRepository.removeKakaoToken()
    }

}