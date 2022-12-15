package com.dev.playground.domain.usecase.photo

import com.dev.playground.domain.model.photo.PhotoDeleteInput
import com.dev.playground.domain.repository.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class RemovePhotoUseCase(
    private val repository: PhotoRepository,
    private val dispatcher: CoroutineDispatcher,
) {

    operator fun invoke(params: String): Flow<Result<String>> = repository.deletePhoto(params).flowOn(dispatcher)

}