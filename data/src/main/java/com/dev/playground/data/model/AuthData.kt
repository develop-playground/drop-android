package com.dev.playground.data.model

import com.dev.playground.domain.model.Token
import com.google.gson.annotations.SerializedName

data class AuthData(
    @SerializedName("grant_type")
    val grantType: String,
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("access_token_expire_time")
    val accessTokenExpireTime: String,
    @SerializedName("refresh_token")
    val refreshToken: String,
    @SerializedName("refresh_token_expire_time")
    val refreshTokenExpireTime: String,
)

fun AuthData.toDomain(): Token {
    return Token(accessToken, refreshToken)
}
