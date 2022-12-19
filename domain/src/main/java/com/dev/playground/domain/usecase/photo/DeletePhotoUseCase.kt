package com.dev.playground.domain.usecase.photo

import com.dev.playground.domain.repository.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File

class DeletePhotoUseCase(
    private val repository: PhotoRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(params: List<File>) = withContext(dispatcher) {
        runCatching {
            repository.deletePhotoList(params)
        }
    }

}