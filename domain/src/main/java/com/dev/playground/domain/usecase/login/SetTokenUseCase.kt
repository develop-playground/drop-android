package com.dev.playground.domain.usecase.login

import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.repository.SharedPreferencesRepository
import com.dev.playground.domain.usecase.base.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher

class SetTokenUseCase(
    private val sharedPreferencesRepository: SharedPreferencesRepository,
    ioDispatcher: CoroutineDispatcher
) : CoroutineUseCase<Auth, Unit>(ioDispatcher) {

    override suspend fun execute(param: Auth) {
        sharedPreferencesRepository.setToken(param)
    }

}