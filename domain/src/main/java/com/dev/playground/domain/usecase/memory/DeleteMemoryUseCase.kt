package com.dev.playground.domain.usecase.memory

import com.dev.playground.domain.repository.MemoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteMemoryUseCase(private val repository: MemoryRepository) {

    suspend operator fun invoke(id: Int) = runCatching {
        repository.deleteMemory(id)
    }
}