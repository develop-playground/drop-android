package com.dev.playground.data.data_source.remote

import androidx.core.net.toUri
import com.dev.playground.domain.model.photo.PhotoDeleteInput
import com.dev.playground.domain.model.photo.PhotoUploadInput
import com.dev.playground.domain.model.photo.PhotoUploadListener
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class FirebaseDataSource {

    companion object {
        private const val PATH_PREFIX = "images/"
    }

    private val storageRef = Firebase.storage.reference

    fun uploadPhotoList(inputList: List<PhotoUploadInput>) = inputList.forEach { input ->
        val uploadTask = storageRef.child("$PATH_PREFIX${input.file.name}").putFile(input.file.toUri())

        uploadTask
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.handleException(input.uploadListener)
                }
                storageRef.downloadUrl
            }
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    input.uploadListener.onSuccess(task.result.toString())
                } else {
                    task.handleException(input.uploadListener)
                }
            }
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

    private fun <T> Task<T>.handleException(listener: PhotoUploadListener) {
        exception?.let {
            listener.onFailure(it)
        }
    }

}