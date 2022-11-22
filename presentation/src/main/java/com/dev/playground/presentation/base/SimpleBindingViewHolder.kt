package com.dev.playground.presentation.base

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.dev.playground.presentation.BR

open class SimpleBindingViewHolder<T>(
    private val binding: ViewDataBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item: T) = with(binding) {
        setVariable(BR.item, item)
        executePendingBindings()
    }

}