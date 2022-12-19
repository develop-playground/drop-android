package com.dev.playground.data.repository

import com.dev.playground.data.data_source.remote.FirebaseDataSource
import com.dev.playground.domain.repository.PhotoRepository
import java.io.File

class PhotoRepositoryImpl(
    private val dataSource: FirebaseDataSource,
) : PhotoRepository {

    override fun uploadPhotoList(params: List<File>) = dataSource.uploadPhotoList(params)

    override fun deletePhotoList(params: List<File>) = dataSource.deletePhotoList(params)

}