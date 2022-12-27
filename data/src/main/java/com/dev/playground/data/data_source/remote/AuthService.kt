package com.dev.playground.data.data_source.remote

import com.dev.playground.data.model.AuthData
import com.dev.playground.data.model.MemberType
import com.dev.playground.data.model.UserData
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {

    @POST("auth/login")
    suspend fun requestLogin(@Body memberType: MemberType): AuthData

    @GET("auth/user")
    suspend fun getUser(): UserData

    @DELETE("auth/user")
    suspend fun deleteUser()

    @POST("auth/logout")
    suspend fun postLogout()

}