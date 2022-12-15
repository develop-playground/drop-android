package com.dev.playground.domain.repository

import kotlinx.coroutines.flow.Flow
import java.io.File

interface PhotoRepository {
    fun uploadPhotoList(inputList: List<File>): Flow<Result<List<String>>>
    fun deletePhoto(input: String): Flow<Result<String>>
}