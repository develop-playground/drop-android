package com.dev.playground.domain.usecase.base

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class NonParamCoroutineUseCase<R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(): Result<R> {
        return runCatching {
            withContext(coroutineDispatcher) {
                execute()
            }
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): R

}
