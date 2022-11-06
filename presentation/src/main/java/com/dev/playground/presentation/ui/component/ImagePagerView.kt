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
        ViewImagePagerBinding.inflate(LayoutInflater.from(context), this)
    }
    private val adapter by lazy { ImagePagerAdapter() }

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        initViews()
    }

    fun submitList(urlList: List<String>) {
        adapter.submitList(listOf("", "", ""))
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
                scaleType = ImageView.ScaleType.CENTER_CROP
            }
        )

        // TODO real data inject
        override fun onBindViewHolder(holder: ImagePagerViewHolder, position: Int) {
            val imageView = (holder.itemView as? ImageView) ?: return
            Glide.with(imageView)
                .load("https://mblogthumb-phinf.pstatic.net/MjAxOTA1MjhfMjg1/MDAxNTU5MDI4NDIwNDQx.As5OFJqnVafj399tvNkZEjjsGDDHnNmI5JXuA8jvorcg.bvAu6JVGfnkS8SO7wazOLIIa-ImAorM23BJzG1rt2Asg.JPEG.designpress2016/DDP_01.jpg?type=w800")
                .into(imageView)
        }

        override fun onViewRecycled(holder: ImagePagerViewHolder) {
            super.onViewRecycled(holder)
            Glide.with(holder.itemView).clear(holder.itemView)
        }

        private class ImagePagerViewHolder(view: ImageView): RecyclerView.ViewHolder(view)

    }

}