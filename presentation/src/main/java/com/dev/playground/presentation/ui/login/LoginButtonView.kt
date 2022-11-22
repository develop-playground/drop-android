package com.dev.playground.presentation.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.dev.playground.presentation.R
import com.dev.playground.presentation.databinding.ViewLoginButtonBinding
import com.dev.playground.presentation.util.RoundRectOutlineProvider

@SuppressLint("CustomViewStyleable")
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
            context.obtainStyledAttributes(attrs, R.styleable.LoginButton, 0, 0)
        setTypeArray(typedArray)
    }

    private fun setTypeArray(typedArray: TypedArray) {
        val imgResId = typedArray.getResourceId(
            R.styleable.LoginButton_symbol,
            R.drawable.ic_kakao_logo
        )
        val text = typedArray.getString(R.styleable.LoginButton_text)
        val textColor = typedArray.getColor(R.styleable.LoginButton_textColor, 0)

        with(binding) {
            ivLoginView.setImageResource(imgResId)
            tvLoginView.text = text
            tvLoginView.setTextColor(textColor)
        }

        typedArray.recycle()
    }
}