package com.dev.playground.domain.usecase.user

import com.dev.playground.domain.model.User
import com.dev.playground.domain.repository.AuthRepository

class GetUserUseCase(
    private val repository: AuthRepository,
) {

    suspend operator fun invoke(): Result<User> = runCatching {
        repository.getUser()
    }

}