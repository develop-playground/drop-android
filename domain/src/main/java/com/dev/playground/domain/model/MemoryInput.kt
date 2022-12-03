package com.dev.playground.domain.model

data class MemoryInput(
    val imageUrlList: List<String>,
    val createdDate: String,
    val address: String,
    val content: String?
)
