package com.dev.playground.presentation.ui.map_container

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
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
        initView()
        getAttrs(attrs)
    }

    private fun initView() = with(binding) {

    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.MarkerView, 0, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {

        val imgResId = typedArray.getResourceId(R.styleable.MarkerView_image, R.color.white)
        val text = typedArray.getString(R.styleable.MarkerView_pointText)

        val bgText = typedArray.getResourceId(
            R.styleable.MarkerView_bgPointText,
            R.drawable.shape_drop_point
        )

        with(binding) {
            if (text.isNullOrEmpty()) tvDropPoint.text = "1"
            else tvDropPoint.text = text

            tvDropPoint.setBackgroundResource(bgText)
            ivMarkerView.setImageResource(imgResId)
        }

        typedArray.recycle()
    }

    fun setMarkerPoint(point: Int) = with(binding) {
        tvDropPoint.text = point.toString()
    }

}