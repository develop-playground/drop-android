package com.dev.playground.data.api

import com.dev.playground.data.model.AuthData
import retrofit2.http.Body
import retrofit2.http.POST

interface DropApi {

    @POST("api/auth/login")
    suspend fun requestLogin(
        @Body memberType: String
    ): AuthData

}