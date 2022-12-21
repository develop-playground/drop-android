package com.dev.playground.data.repository

import com.dev.playground.data.data_source.remote.AuthService
import com.dev.playground.data.model.MemberType
import com.dev.playground.data.model.toDomain
import com.dev.playground.domain.model.User
import com.dev.playground.domain.repository.AuthRepository

class AuthRepositoryImpl(private val service: AuthService) : AuthRepository {

    override suspend fun requestLogin(memberType: String) = service.requestLogin(MemberType(memberType)).toDomain()

    override suspend fun getUser(): User = service.getUser().toDomain()

}