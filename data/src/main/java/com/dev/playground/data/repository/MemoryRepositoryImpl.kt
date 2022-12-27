package com.dev.playground.data.repository

import com.dev.playground.data.data_source.remote.MemoryService
import com.dev.playground.data.model.MemoryData
import com.dev.playground.data.model.toDomain
import com.dev.playground.domain.model.Memory
import com.dev.playground.domain.model.MemoryInput
import com.dev.playground.domain.repository.MemoryRepository

class MemoryRepositoryImpl(private val service: MemoryService) : MemoryRepository {

    override suspend fun getMemoryList(page: Int): List<Memory> = service.getMemoryList(page).map(MemoryData::toDomain)

    override suspend fun postMemory(params: MemoryInput): Memory = service.postMemory(params).toDomain()

    override suspend fun deleteMemory(params: Int) = service.deleteMemory(params)

}