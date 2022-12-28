package com.dev.playground.domain.usecase.memory

import com.dev.playground.domain.repository.MemoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMemoryListUseCase(private val repository: MemoryRepository) {

    suspend operator fun invoke(param: Int) = runCatching {
        repository.getMemoryList(param)
    }

}