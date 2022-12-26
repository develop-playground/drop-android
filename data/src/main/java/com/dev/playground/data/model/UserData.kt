package com.dev.playground.data.model

import com.dev.playground.domain.model.User

data class UserData(
    val id: Int,
    val email: String,
)

fun UserData.toDomain() = User(email)