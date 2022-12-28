package com.dev.playground.presentation.model

import com.dev.playground.presentation.R
import com.dev.playground.presentation.model.base.SimpleUIModel

data class ModifyPhotoUIModel(
    val url: String
) : SimpleUIModel(R.layout.item_modify_photo)