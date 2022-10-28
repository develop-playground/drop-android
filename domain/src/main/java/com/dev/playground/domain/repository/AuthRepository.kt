package com.dev.playground.domain.repository

import com.dev.playground.domain.model.Auth

interface AuthRepository {

    suspend fun requestLogin(memberType: String): Auth

}