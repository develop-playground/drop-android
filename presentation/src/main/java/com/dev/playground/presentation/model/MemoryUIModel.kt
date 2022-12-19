package com.dev.playground.presentation.model

import com.dev.playground.domain.model.Memory
import com.dev.playground.presentation.R
import com.dev.playground.presentation.model.base.SimpleItemDiffCallback
import com.dev.playground.presentation.model.base.SimpleUIModel

data class MemoryUIModel(
    val id: Int,
    val imageList: List<ImageCarouselItemUIModel>,
    val content: String,
    val location: Memory.Location,
    val address: String,
    val createdDate: String,
    val onClickEdit: (Int) -> Unit,
    val onClickRemove: (Int) -> Unit,
) : SimpleUIModel(R.layout.item_memory) {

    override fun areItemsTheSame(other: SimpleItemDiffCallback.DiffCallback): Boolean {
        return if (other is MemoryUIModel) {
            other.id == this.id
        } else {
            super.areItemsTheSame(other)
        }
    }

}

fun Memory.toPresentation(
    onClickEdit: (Int) -> Unit,
    onClickRemove: (Int) -> Unit,
) = MemoryUIModel(
    id = id,
    imageList = imageUrlList.map { ImageCarouselItemUIModel(it) },
    content = content,
    location = location,
    address = address,
    createdDate = createdDate,
    onClickEdit = onClickEdit,
    onClickRemove = onClickRemove
)