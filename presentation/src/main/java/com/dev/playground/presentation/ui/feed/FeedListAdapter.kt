package com.dev.playground.presentation.ui.feed

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dev.playground.presentation.base.SimpleItemDiffCallback
import com.dev.playground.presentation.databinding.ItemPostGroupBinding
import com.dev.playground.presentation.model.PostGroupUIModel

class FeedListAdapter : ListAdapter<PostGroupUIModel, FeedListAdapter.FeedViewHolder>(SimpleItemDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val binding = ItemPostGroupBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FeedViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        holder.bind(currentList.getOrNull(position))
    }

    class FeedViewHolder(
        private val binding: ItemPostGroupBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: PostGroupUIModel?) = with(binding) {
            model ?: return@with
            tvPostGroupTitle.text = model.title
            tvPostGroupLocation.text = model.location
            cvImagePager.submitList(model.urlList)
        }
    }

}