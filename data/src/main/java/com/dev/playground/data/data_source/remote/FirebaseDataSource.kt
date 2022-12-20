package com.dev.playground.data.data_source.remote

import androidx.core.net.toUri
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.File

class FirebaseDataSource {

    companion object {
        private const val PATH_PREFIX = "images/"
    }

    private val storageRef = Firebase.storage.reference

    fun uploadPhotoList(photoList: List<File>): Flow<Result<List<String>>> = flow {
        val resultList = photoList.map {
            storageRef
                .child("$PATH_PREFIX${it.name}")
                .putFile(it.toUri())
                .await()
                .storage
                .downloadUrl
                .await()
                .toString()
        }
        emit(Result.success(resultList))
    }.catch {
        emit(Result.failure(it))
    }

    fun deletePhotoList(photoList: List<File>) = photoList.forEach {
        storageRef
            .child("$PATH_PREFIX${it.name}")
            .delete()
    }
}