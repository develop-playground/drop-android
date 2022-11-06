package com.dev.playground.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.dev.playground.presentation.model.base.SimpleItemDiffCallback
import com.dev.playground.presentation.model.base.SimpleUIModel

open class SimpleBindingAdapter<T, VH>(
    private val viewHolderClazz: Class<VH>? = null,
) : ListAdapter<T, VH>(
    SimpleItemDiffCallback()
) where T : SimpleItemDiffCallback.DiffCallback, T : SimpleUIModel, VH : SimpleBindingViewHolder<T> {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            layoutInflater,
            viewType,
            parent,
            false
        )
        return (viewHolderClazz?.getDeclaredConstructor(ViewDataBinding::class.java)?.newInstance(binding) ?: SimpleBindingViewHolder(binding)) as VH
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = getItem(position).layoutRes
}