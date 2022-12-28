package com.dev.playground.domain.model

data class Token(
    val accessToken: String,
    val refreshToken: String,
) {

    fun getHeaderToken(encodedPath: String) = if (NeedRefreshTokenUrl.values().any { encodedPath.contains(it.value) }) {
        refreshToken
    } else {
        accessToken
    }

    private enum class NeedRefreshTokenUrl(val value: String) {
        Logout("/auth/logout"),
        User("/auth/user"),
        Token("/auth/token/reissue")
    }

}
