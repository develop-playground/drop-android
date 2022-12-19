package com.dev.playground.data.repository

import com.dev.playground.data.data_source.remote.FirebaseDataSource
import com.dev.playground.domain.repository.PhotoRepository
import java.io.File

class PhotoRepositoryImpl(
    private val dataSource: FirebaseDataSource,
) : PhotoRepository {

    override fun uploadPhotoList(inputList: List<File>) = dataSource.uploadPhotoList(inputList)

}