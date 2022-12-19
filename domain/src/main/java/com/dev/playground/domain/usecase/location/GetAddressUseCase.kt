package com.dev.playground.domain.usecase.location

import com.dev.playground.domain.model.Memory
import com.dev.playground.domain.repository.LocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class GetAddressUseCase(
    private val repository: LocationRepository,
    private val dispatcher: CoroutineDispatcher,
) {

    suspend operator fun invoke(params: Memory.Location) = withContext(dispatcher) {
        runCatching {
            repository.getAddress(params)
        }
    }

}