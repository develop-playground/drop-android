package com.dev.playground.domain.usecase.memory

import com.dev.playground.domain.repository.MemoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class DeleteMemoryUseCase(
    private val repository: MemoryRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(id: Int) = withContext(dispatcher) {
        runCatching {
            repository.deleteMemory(id)
        }
    }
}