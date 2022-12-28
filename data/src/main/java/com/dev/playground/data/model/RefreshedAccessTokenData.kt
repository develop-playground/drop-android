package com.dev.playground.data.model

import com.google.gson.annotations.SerializedName

data class RefreshedAccessTokenData(
    @SerializedName("access_token")
    val accessToken: String
)
