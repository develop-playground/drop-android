package com.dev.playground.presentation.util

import android.graphics.drawable.ColorDrawable
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