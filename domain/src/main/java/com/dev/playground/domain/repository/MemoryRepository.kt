package com.dev.playground.domain.repository

import com.dev.playground.domain.model.Memory

interface MemoryRepository {

    suspend fun getMemoryList(page: Int): List<Memory>

}