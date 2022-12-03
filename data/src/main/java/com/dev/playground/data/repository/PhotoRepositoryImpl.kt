package com.dev.playground.data.repository

import com.dev.playground.data.data_source.remote.FirebaseDataSource
import com.dev.playground.domain.model.photo.PhotoDeleteInput
import com.dev.playground.domain.model.photo.PhotoUploadInput
import com.dev.playground.domain.repository.PhotoRepository

class PhotoRepositoryImpl(
    private val dataSource: FirebaseDataSource
): PhotoRepository {

    override fun uploadPhotoList(inputList: List<PhotoUploadInput>) = dataSource.uploadPhotoList(inputList)

    override fun deletePhoto(input: PhotoDeleteInput) = dataSource.deletePhoto(input)

}