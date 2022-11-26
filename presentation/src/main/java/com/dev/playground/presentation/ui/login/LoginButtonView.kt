package com.dev.playground.presentation.ui.login

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.dev.playground.presentation.R
import com.dev.playground.presentation.databinding.ViewLoginButtonBinding
import com.dev.playground.presentation.util.RoundRectOutlineProvider

class LoginButtonView
@JvmOverloads
constructor(
    context: Context,
    attr: AttributeSet? = null,
) : ConstraintLayout(context, attr) {

    private lateinit var binding: ViewLoginButtonBinding

    init {
        initView()
        getAttrs(attr)
    }

    private fun initView() {
        binding = ViewLoginButtonBinding.inflate(LayoutInflater.from(context), this, true)
        outlineProvider = RoundRectOutlineProvider()
    }

    private fun getAttrs(attrs: AttributeSet?) {
        val typedArray =
            context.obtainStyledAttributes(attrs, R.styleable.LoginButtonView, 0, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val imgResId = typedArray.getResourceId(R.styleable.LoginButtonView_symbol, R.drawable.ic_kakao_logo)
        val text = typedArray.getString(R.styleable.LoginButtonView_text)
        val textColor = typedArray.getColor(R.styleable.LoginButtonView_textColor, ContextCompat.getColor(context, R.color.kakao_brown))

        with(binding) {
            ivLoginView.setImageResource(imgResId)
            tvLoginView.text = text
            tvLoginView.setTextColor(textColor)
        }

        typedArray.recycle()
    }
}