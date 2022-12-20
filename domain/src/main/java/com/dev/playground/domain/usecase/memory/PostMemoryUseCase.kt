package com.dev.playground.domain.usecase.memory

import com.dev.playground.domain.model.MemoryInput
import com.dev.playground.domain.repository.MemoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PostMemoryUseCase(
    private val repository: MemoryRepository,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(params: MemoryInput) = withContext(dispatcher) {
        runCatching {
            repository.postMemory(params)
        }
    }

}