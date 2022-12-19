package com.dev.playground.domain.repository

import com.dev.playground.domain.model.Memory

interface LocationRepository {
    suspend fun getAddress(params: Memory.Location): String
}