package com.dev.playground.domain.usecase.memory

import com.dev.playground.domain.repository.MemoryRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetMemoryListUseCase(
    private val repository: MemoryRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {

    suspend operator fun invoke(param: Int) = withContext(ioDispatcher) {
        runCatching {
            repository.getMemoryList(param)
        }
    }

}