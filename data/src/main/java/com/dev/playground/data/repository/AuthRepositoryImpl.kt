package com.dev.playground.data.repository

import com.dev.playground.data.api.DropApi
import com.dev.playground.data.model.MemberType
import com.dev.playground.data.model.toDomain
import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.repository.AuthRepository

class AuthRepositoryImpl(
    private val dropApi: DropApi
) : AuthRepository{

    override suspend fun requestLogin(memberType: String): Auth {
        return dropApi.requestLogin(MemberType(memberType)).toDomain()
    }

}