package com.dev.playground.domain.repository

import com.dev.playground.domain.model.Token
import com.dev.playground.domain.model.User

interface AuthRepository {

    suspend fun requestLogin(memberType: String): Token

    suspend fun getUser(): User

    suspend fun deleteUser()

    suspend fun postLogout()

}