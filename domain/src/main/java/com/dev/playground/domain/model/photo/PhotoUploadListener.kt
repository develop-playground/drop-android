package com.dev.playground.domain.model.photo

interface PhotoUploadListener {
    fun onSuccess(url: String)
    fun onFailure(throwable: Throwable)
}