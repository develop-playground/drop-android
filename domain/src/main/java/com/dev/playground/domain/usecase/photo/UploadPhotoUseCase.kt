package com.dev.playground.domain.usecase.photo

import com.dev.playground.domain.repository.PhotoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import java.io.File

class UploadPhotoUseCase(
    private val repository: PhotoRepository,
    private val dispatcher: CoroutineDispatcher,
) {

    operator fun invoke(params: List<File>): Flow<Result<List<String>>> = repository.uploadPhotoList(params).flowOn(dispatcher)

}