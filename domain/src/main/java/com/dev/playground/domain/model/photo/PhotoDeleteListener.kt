package com.dev.playground.domain.model.photo

interface PhotoDeleteListener {
    fun onSuccess()
    fun onFailure(throwable: Throwable)
}