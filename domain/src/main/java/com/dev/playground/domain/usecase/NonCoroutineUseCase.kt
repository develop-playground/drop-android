package com.dev.playground.domain.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class NonCoroutineUseCase<R>(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend operator fun invoke(): Result<R> {
        return try {
            withContext(coroutineDispatcher) {
                execute().let {
                    Result.success(it)
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @Throws(RuntimeException::class)
    protected abstract suspend fun execute(): R

}