package com.dev.playground.data.api

import com.dev.playground.data.model.AuthData
import com.dev.playground.data.model.MemberType
import com.dev.playground.data.model.MemoryData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthService {

    @POST("auth/login")
    suspend fun requestLogin(@Body memberType: MemberType): AuthData

}