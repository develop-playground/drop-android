package com.dev.playground.presentation.ui.map_container

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dev.playground.presentation.R
import com.dev.playground.presentation.databinding.ViewMarkerBinding

class MarkerView
@JvmOverloads
constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : ConstraintLayout(context, attrs) {

    private val binding by lazy {
        ViewMarkerBinding.inflate(LayoutInflater.from(context), this, true)
    }

    init {
        minHeight = resources.getDimensionPixelSize(R.dimen.map_container_marker_image_size)
        minWidth = resources.getDimensionPixelSize(R.dimen.map_container_marker_image_size)
    }

    fun setClusterItem(item: DropClusterItem, count: Int? = null, onResourceReady: (View) -> Unit) = apply {
        setPoint(count)
        setImage(item.imageUrl, onResourceReady)
    }

    fun setImage(url: String?, onResourceReady: (View) -> Unit) {
        Glide.with(context)
            .asBitmap()
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(ContextCompat.getDrawable(context, R.drawable.ic_drop_logo_black))
            .centerCrop()
            .into(
                object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        binding.ivMarkerView.setImageBitmap(resource)
                        onResourceReady.invoke(this@MarkerView)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // no-op
                    }
                }
            )
    }

    fun setPoint(count: Int? = 0) = with(binding.tvDropPoint) {
        text = count?.toString().orEmpty()
        isVisible = count != null
    }

}