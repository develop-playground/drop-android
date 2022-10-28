package com.dev.playground.domain.model

data class Auth(
    val accessToken: String,
    val refreshToken: String,
)
