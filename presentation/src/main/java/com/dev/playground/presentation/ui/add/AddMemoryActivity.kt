package com.dev.playground.presentation.ui.add

import android.os.Bundle
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.databinding.ActivityAddMemoryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddMemoryActivity : BaseActivity<ActivityAddMemoryBinding>(R.layout.activity_add_memory) {

    private val viewModel by viewModel<AddMemoryViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initCollects()
    }

    private fun initViews() = with(binding) {
        vm = viewModel

        tvClose.setOnClickListener {
            finish()
        }
        flAddMemory.setOnClickListener {

        }
        btAddMemory.setOnClickListener {

        }
    }

    private fun initCollects() {

    }


}