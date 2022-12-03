package com.dev.playground.data.api

import com.dev.playground.data.model.AuthData
import com.dev.playground.data.model.MemberType
import retrofit2.http.Body
import retrofit2.http.POST

interface DropApi {

    @POST("auth/login")
    suspend fun requestLogin(
        @Body memberType: MemberType
    ): AuthData

}