package com.dev.playground.presentation.ui.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.databinding.DataBindingUtil
import com.dev.playground.presentation.R
import com.dev.playground.presentation.databinding.DialogDropBinding

class DropDialog(context: Context) : Dialog(context) {

    companion object {
        private const val EMPTY = ""
    }

    private lateinit var binding: DialogDropBinding

    var contentText: String = EMPTY
    var leftText: String = EMPTY
    var rightText: String = EMPTY

    var onLeftClick: (() -> Unit)? = null
    var onRightClick: (() -> Unit)? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.inflate(
            LayoutInflater.from(context),
            R.layout.dialog_drop,
            null,
            false
        )
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() = with(binding) {
        tvContent.text = contentText
        tvLeft.text = leftText
        tvRight.text = rightText

        tvLeft.setOnClickListener {
            onLeftClick?.invoke()
            dismiss()
        }
        tvRight.setOnClickListener {
            onRightClick?.invoke()
            dismiss()
        }
    }

}

fun DropDialog.show(action: DropDialog.() -> Unit) = DropDialog(context).apply(action).show()