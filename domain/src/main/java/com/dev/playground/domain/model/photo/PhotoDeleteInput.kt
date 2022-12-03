package com.dev.playground.domain.model.photo

data class PhotoDeleteInput(
    val fileName: String,
    val deleteListener: PhotoDeleteListener
)