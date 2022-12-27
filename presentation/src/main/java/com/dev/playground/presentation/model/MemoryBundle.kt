package com.dev.playground.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemoryBundle(
    val id: Int,
    val urlList: List<String>,
    val content: String,
    val address: String,
    val createdDate: String,
) : Parcelable

fun MemoryUIModel.toBundle() = MemoryBundle(
    id = id,
    urlList = imageList.map { it.url },
    content = content,
    address = address,
    createdDate = createdDate
)