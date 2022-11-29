package com.dev.playground.presentation.util

import android.graphics.Outline
import android.graphics.drawable.ColorDrawable
import android.view.RoundedCorner
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.dev.playground.presentation.R

@BindingAdapter("imageUrl")
fun ImageView.setImageUrl(url: String?) {
    url ?: return
    Glide.with(this)
        .load(url)
        .centerCrop()
        .placeholder(ColorDrawable(ContextCompat.getColor(context, R.color.gray_medium)))
        .into(this)
}

@BindingAdapter("radius")
fun View.setRadius(radius: Float?) {
    radius ?: return
    clipToOutline = true
    outlineProvider = object : ViewOutlineProvider() {
        override fun getOutline(view: View?, outline: Outline?) {
            view ?: return
            outline?.setRoundRect(0, 0, view.width, view.height, radius)
        }
    }
}