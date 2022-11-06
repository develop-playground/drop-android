package com.dev.playground.presentation.model

import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.SimpleItemDiffCallback
import com.dev.playground.presentation.base.SimpleUIModel

data class PostGroupUIModel(
    val id: String,
    val title: String,
    val description: String,
    val location: String,
    val urlList: List<String>,
    val onEditPost: (String) -> Unit
) : SimpleUIModel(R.layout.item_post_group), SimpleItemDiffCallback.DiffCallback