package com.dev.playground.presentation.model

import com.dev.playground.domain.model.Memory
import com.dev.playground.presentation.R
import com.dev.playground.presentation.model.base.SimpleItemDiffCallback
import com.dev.playground.presentation.model.base.SimpleUIModel

data class MemoryUIModel(
    val id: Int,
    val imageList: List<ImageCarouselItemUIModel>,
    val content: String,
    val location: String,
    val createdDate: String,
    val onClickEdit: (Int) -> Unit,
    val onClickRemove: (Int) -> Unit,
) : SimpleUIModel(R.layout.item_memory), SimpleItemDiffCallback.DiffCallback

fun Memory.toPresentation(
    onClickEdit: (Int) -> Unit,
    onClickRemove: (Int) -> Unit
) = run {
    MemoryUIModel(
        id = id,
        imageList = imageUrlList.map { ImageCarouselItemUIModel(it) },
        content = content,
        // TODO real data 사용
        location = "TEST",
        createdDate = createdDate,
        onClickEdit = onClickEdit,
        onClickRemove = onClickRemove
    )
}