package com.dev.playground.presentation.model

import com.dev.playground.presentation.R
import com.dev.playground.presentation.model.base.SimpleItemDiffCallback
import com.dev.playground.presentation.model.base.SimpleUIModel

data class MemoryUIModel(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val imageList: List<ImageCarouselItemUIModel>,
    val onEditMemory: (String) -> Unit
) : SimpleUIModel(R.layout.item_memory), SimpleItemDiffCallback.DiffCallback