package com.dev.playground.data.data_source.remote

import androidx.core.net.toUri
import com.dev.playground.domain.model.photo.PhotoDeleteInput
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import java.io.File

class FirebaseDataSource {

    companion object {
        private const val PATH_PREFIX = "images/"
    }

    private val storageRef = Firebase.storage.reference

    fun uploadPhotoList(photoList: List<File>): Flow<List<String?>> = flow {
        val resultList = photoList.map {
            storageRef
                .child("$PATH_PREFIX${it.name}")
                .putFile(it.toUri())
                .await()
                .storage
                .downloadUrl
                .await()
                ?.toString()
        }
        emit(resultList)
    }

    fun deletePhoto(input: PhotoDeleteInput) {
        storageRef
            .child("$PATH_PREFIX${input.fileName}")
            .delete()
            .addOnSuccessListener {
                input.deleteListener.onSuccess()
            }
            .addOnFailureListener {
                input.deleteListener.onFailure(it)
            }
    }

}