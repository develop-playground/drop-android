package com.dev.playground.presentation.model

import com.dev.playground.domain.model.Memory
import com.dev.playground.presentation.R
import com.dev.playground.presentation.model.base.SimpleItemDiffCallback
import com.dev.playground.presentation.model.base.SimpleUIModel
import java.text.SimpleDateFormat
import java.util.*

data class MemoryUIModel(
    val id: Int,
    val imageList: List<ImageCarouselItemUIModel>,
    val content: String,
    val address: String,
    val createdDate: String,
    val onClickEdit: (MemoryBundle) -> Unit,
    val onClickRemove: (Int) -> Unit,
) : SimpleUIModel(R.layout.item_memory) {

    override fun areItemsTheSame(other: SimpleItemDiffCallback.DiffCallback): Boolean {
        return if (other is MemoryUIModel) {
            other.id == this.id
        } else {
            super.areItemsTheSame(other)
        }
    }

    fun formatCreatedDate(): String = try {
        val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formatter = SimpleDateFormat("yy.MM.dd", Locale.getDefault())
        val parsedString = parser.parse(createdDate) ?: return createdDate
        formatter.format(parsedString)
    } finally {
        createdDate
    }

}

fun Memory.toPresentation(
    onClickEdit: (MemoryBundle) -> Unit,
    onClickRemove: (Int) -> Unit,
) = MemoryUIModel(
    id = id,
    imageList = imageUrlList.map { ImageCarouselItemUIModel(it) },
    content = content,
    address = address,
    createdDate = createdDate,
    onClickEdit = onClickEdit,
    onClickRemove = onClickRemove
)