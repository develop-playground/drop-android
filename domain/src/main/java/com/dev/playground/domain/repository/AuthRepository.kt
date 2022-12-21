package com.dev.playground.domain.repository

import com.dev.playground.domain.model.Auth
import com.dev.playground.domain.model.User

interface AuthRepository {

    suspend fun requestLogin(memberType: String): Auth

    suspend fun getUser(): User
}