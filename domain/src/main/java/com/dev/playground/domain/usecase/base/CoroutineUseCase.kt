package com.dev.playground.domain.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class CoroutineUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(parameters: P): Result<R> {
        return runCatching {
            withContext(coroutineDispatcher) {
                execute(parameters)
            }
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(param: P): R

}
