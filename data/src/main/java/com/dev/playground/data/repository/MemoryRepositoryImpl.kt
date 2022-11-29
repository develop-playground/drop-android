package com.dev.playground.data.repository

import com.dev.playground.data.api.DropApi
import com.dev.playground.data.model.MemoryData
import com.dev.playground.data.model.toDomain
import com.dev.playground.domain.model.Memory
import com.dev.playground.domain.repository.MemoryRepository

class MemoryRepositoryImpl(private val api: DropApi): MemoryRepository {

    override suspend fun getMemoryList(page: Int): List<Memory> = api.getMemoryList(page).map(MemoryData::toDomain)

}