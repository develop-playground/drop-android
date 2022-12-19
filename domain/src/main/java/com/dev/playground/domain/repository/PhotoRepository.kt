package com.dev.playground.domain.repository

import kotlinx.coroutines.flow.Flow
import java.io.File

interface PhotoRepository {
    fun uploadPhotoList(params: List<File>): Flow<Result<List<String>>>
    fun deletePhotoList(params: List<File>)
}