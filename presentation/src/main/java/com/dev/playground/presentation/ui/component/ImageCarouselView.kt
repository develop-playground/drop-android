package com.dev.playground.presentation.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dev.playground.presentation.base.SimpleBindingAdapter
import com.dev.playground.presentation.base.SimpleBindingViewHolder
import com.dev.playground.presentation.databinding.ViewImageCarouselBinding
import com.dev.playground.presentation.model.ImageCarouselItemUIModel
import com.google.android.material.tabs.TabLayoutMediator

class ImageCarouselView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by lazy {
        ViewImageCarouselBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private val adapter by lazy { SimpleBindingAdapter<ImageCarouselItemUIModel, SimpleBindingViewHolder<ImageCarouselItemUIModel>>() }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        initViews()
    }

    fun setItemList(itemList: List<ImageCarouselItemUIModel>) {
        adapter.submitList(itemList)
    }

    private fun initViews() = with(binding) {
        vpImagePager.adapter = adapter
        TabLayoutMediator(tlImagePager, vpImagePager) { _, _ -> }.attach()
    }

}