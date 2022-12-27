package com.dev.playground.data.repository

import com.dev.playground.data.data_source.remote.MemoryService
import com.dev.playground.data.model.MemoryData
import com.dev.playground.data.model.toDomain
import com.dev.playground.data.util.AutoTokenRefresher
import com.dev.playground.domain.model.Memory
import com.dev.playground.domain.model.MemoryInput
import com.dev.playground.domain.repository.MemoryRepository

class MemoryRepositoryImpl(
    private val service: MemoryService,
    private val refresher: AutoTokenRefresher
) : MemoryRepository {

    override suspend fun getMemoryList(page: Int): List<Memory> = refresher.authHandle {
        service.getMemoryList(page).map(MemoryData::toDomain)
    }

    override suspend fun postMemory(params: MemoryInput): Memory = refresher.authHandle {
        service.postMemory(params).toDomain()
    }

    override suspend fun deleteMemory(params: Int) = refresher.authHandle {
        service.deleteMemory(params)
    }

}