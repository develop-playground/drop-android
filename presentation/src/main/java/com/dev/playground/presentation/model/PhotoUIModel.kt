package com.dev.playground.presentation.model

import com.dev.playground.presentation.R
import com.dev.playground.presentation.model.base.SimpleItemDiffCallback
import com.dev.playground.presentation.model.base.SimpleUIModel
import java.io.File

data class PhotoUIModel(
    val index: Int,
    val file: File,
    val onClickRemove: (Int) -> Unit,
) : SimpleUIModel(R.layout.item_photo)