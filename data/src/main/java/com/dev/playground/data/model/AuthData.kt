package com.dev.playground.data.model

import com.dev.playground.domain.model.Auth

data class AuthData(
    val grantType: String,
    val accessToken: String,
    val accessTokenExpireTime: String,
    val refreshToken: String,
    val refreshTokenExpireTime: String,
)

fun AuthData.toDomain(): Auth {
    return Auth(accessToken, refreshToken)
}
