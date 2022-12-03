package com.dev.playground.domain.model

data class Memory(
    val id: Int,
    val imageUrlList: List<String>,
    val content: String,
    val location: Location,
    val address: String,
    val createdDate: String,
) {
    data class Location(
        val latitude: Double,
        val longitude: Double,
    )
}
