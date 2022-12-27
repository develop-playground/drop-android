package com.dev.playground.domain.usecase.memory

import com.dev.playground.domain.model.MemoryModifyInput
import com.dev.playground.domain.repository.MemoryRepository

class ModifyMemoryUseCase(
    private val repository: MemoryRepository
) {

    suspend operator fun invoke(input: MemoryModifyInput) = runCatching {
        repository.patchMemory(input)
    }

}