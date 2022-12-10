package com.dev.playground.data.data_source.remote

import com.dev.playground.data.model.AuthData
import com.dev.playground.data.model.MemberType
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun requestLogin(@Body memberType: MemberType): AuthData

}