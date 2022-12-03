package com.dev.playground.data.repository

import com.dev.playground.data.api.AuthService
import com.dev.playground.data.model.MemberType
import com.dev.playground.data.model.toDomain
import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val authService: AuthService
) : AuthRepository{

    override suspend fun requestLogin(memberType: String): Auth {
        return authService.requestLogin(MemberType(memberType)).toDomain()
    }

}