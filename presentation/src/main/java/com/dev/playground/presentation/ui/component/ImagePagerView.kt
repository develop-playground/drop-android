package com.dev.playground.presentation.ui.component

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dev.playground.presentation.databinding.ViewImagePagerBinding
import com.google.android.material.tabs.TabLayoutMediator

class ImagePagerView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding by lazy {
        ViewImagePagerBinding.inflate(LayoutInflater.from(context), this, true)
    }
    private val adapter by lazy { ImagePagerAdapter() }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        initViews()
    }

    fun setItemList(urlList: List<String>) {
        adapter.submitList(urlList)
    }

    private fun initViews() = with(binding) {
        vpImagePager.adapter = adapter
        TabLayoutMediator(tlImagePager, vpImagePager) { _, _ -> }.attach()
    }

    private class ImagePagerAdapter: ListAdapter<String, ImagePagerAdapter.ImagePagerViewHolder>(DIFF_CALLBACK) {

        companion object {
            private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<String>() {
                override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
                override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ImagePagerViewHolder(
            ImageView(parent.context).apply {
                layoutParams = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        )

        override fun onBindViewHolder(holder: ImagePagerViewHolder, position: Int) {
            val imageView = (holder.itemView as? ImageView) ?: return
            Glide.with(imageView)
                .load(getItem(position))
                .into(imageView)
        }

        override fun onViewRecycled(holder: ImagePagerViewHolder) {
            super.onViewRecycled(holder)
            Glide.with(holder.itemView).clear(holder.itemView)
        }

        private class ImagePagerViewHolder(view: ImageView): RecyclerView.ViewHolder(view)

    }

}