package com.dev.playground.domain.model

data class MemoryInput(
    val imageUrls: List<String>,
    val content: String,
    val location: Memory.Location,
    val address: String,
)
