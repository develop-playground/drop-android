package com.dev.playground.domain.usecase.memory

import com.dev.playground.domain.model.Memory
import com.dev.playground.domain.repository.MemoryRepository
import com.dev.playground.domain.usecase.base.CoroutineUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class GetMemoryListUseCase(
    private val repository: MemoryRepository,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoroutineUseCase<Int, List<Memory>>(ioDispatcher) {

    override suspend fun execute(param: Int): List<Memory> = repository.getMemoryList(param)

}