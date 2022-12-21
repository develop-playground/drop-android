package com.dev.playground.domain.usecase.user

import com.dev.playground.domain.repository.AuthRepository
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class GetUserEmailUseCase(
    private val repository: AuthRepository,
) {

    operator fun invoke() = flow {
        emit(repository.getUser().email)
    }

}