package com.dev.playground.data.repository

import com.dev.playground.data.data_source.remote.AuthService
import com.dev.playground.data.model.MemberType
import com.dev.playground.data.model.toDomain
import com.dev.playground.data.util.AutoTokenRefresher
import com.dev.playground.domain.model.User
import com.dev.playground.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val service: AuthService,
    private val refresher: AutoTokenRefresher,
) : AuthRepository {

    override suspend fun requestLogin(memberType: String) = service.requestLogin(MemberType(memberType)).toDomain()

    override suspend fun getUser(): User = refresher.authHandle { service.getUser().toDomain() }

    override suspend fun deleteUser() = refresher.authHandle { service.deleteUser() }

    override suspend fun postLogout() = refresher.authHandle { service.postLogout() }

    override suspend fun refreshAccessToken(): String = service.refreshAccessToken().accessToken

}