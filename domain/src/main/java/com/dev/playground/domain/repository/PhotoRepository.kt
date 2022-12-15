package com.dev.playground.domain.repository

import com.dev.playground.domain.model.photo.PhotoDeleteInput
import kotlinx.coroutines.flow.Flow
import java.io.File

interface PhotoRepository {
    fun uploadPhotoList(inputList: List<File>): Flow<List<String?>>
    fun deletePhoto(input: PhotoDeleteInput)
}