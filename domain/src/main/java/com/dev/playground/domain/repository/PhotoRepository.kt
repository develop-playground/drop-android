package com.dev.playground.domain.repository

import com.dev.playground.domain.model.photo.PhotoDeleteInput
import com.dev.playground.domain.model.photo.PhotoUploadInput

interface PhotoRepository {
    fun uploadPhotoList(inputList: List<PhotoUploadInput>)
    fun deletePhoto(input: PhotoDeleteInput)
}