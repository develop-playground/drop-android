package com.dev.playground.data.api

import retrofit2.http.Body
import retrofit2.http.POST

interface DropApi {

    @POST("api/auth/login")
    suspend fun requestLogin()

}