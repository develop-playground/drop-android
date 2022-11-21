package com.dev.playground.presentation.model.base

import androidx.recyclerview.widget.DiffUtil

class SimpleItemDiffCallback<T : SimpleItemDiffCallback.DiffCallback> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areItemsTheSame(newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.areContentsTheSame(newItem)
    }

    interface DiffCallback {
        fun areItemsTheSame(other: DiffCallback) = (this::class == other::class)
        fun areContentsTheSame(other: DiffCallback) = (this == other)
    }
}