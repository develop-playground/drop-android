package com.dev.playground.domain.usecase.user.login

import com.dev.playground.domain.model.type.LoginType
import com.dev.playground.domain.repository.SharedPreferencesRepository
import kotlinx.coroutines.flow.flow

class GetLoginTypeUseCase(
    private val repository: SharedPreferencesRepository,
) {

    operator fun invoke() = flow {
        val type = repository.getLoginType()
        val foundType = LoginType.values().find { it.name == type } ?: throw NoSuchElementException()
        emit(foundType)
    }

}