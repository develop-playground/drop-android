package com.dev.playground.data.model

import com.dev.playground.domain.model.Memory
import com.google.gson.annotations.SerializedName

data class MemoryData(
    val id: Int,
    @SerializedName("image_urls")
    val imageUrlList: List<String>,
    val content: String,
    val location: LocationData,
    @SerializedName("created_date")
    val createdDate: String,
) {

    data class LocationData(
        val latitude: Double,
        val longitude: Double,
    )

}

fun MemoryData.toDomain() = run {
    Memory(
        id = id,
        imageUrlList = imageUrlList,
        content = content,
        location = Memory.Location(
            latitude = location.latitude,
            longitude = location.longitude
        ),
        createdDate = createdDate
    )
}