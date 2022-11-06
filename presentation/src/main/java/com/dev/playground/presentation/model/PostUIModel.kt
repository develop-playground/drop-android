package com.dev.playground.presentation.model

import androidx.annotation.LayoutRes
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.SimpleUIModel

// TODO POST 그룹으로 묶어서 표현g
sealed class PostUIModel(
    @LayoutRes
    layoutRes: Int,
) : SimpleUIModel(layoutRes) {

    data class PostTitleUIModel(
        val title: String,
        val location: String,
        val onClickEdit: (String) -> Unit,
    ) : PostUIModel(R.layout.item_post_title)

    data class PostImageCarouselUIModel(
        val urlList: List<String>,
    ) : PostUIModel(R.layout.item_post_image_carousel)

    data class PostDescriptionUIModel(
        val description: String,
    ) : PostUIModel(R.layout.item_post_description)

}