package com.dev.playground.domain.model.photo

import java.io.File

data class PhotoUploadInput(
    val file: File,
    val uploadListener: PhotoUploadListener
)