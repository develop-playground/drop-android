package com.dev.playground.domain.repository

import com.dev.playground.domain.model.Memory
import com.dev.playground.domain.model.MemoryInput
import com.dev.playground.domain.model.MemoryModifyInput

interface MemoryRepository {

    suspend fun getMemoryList(page: Int): List<Memory>

    suspend fun postMemory(params: MemoryInput): Memory

    suspend fun patchMemory(params: MemoryModifyInput)

    suspend fun deleteMemory(params: Int)

}