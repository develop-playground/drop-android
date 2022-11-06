package com.dev.playground.presentation.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes private val layoutId: Int,
) : Fragment(layoutId) {

    protected lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = DataBindingUtil.inflate<B>(inflater, layoutId, container, false).apply {
        binding = this
        lifecycleOwner = viewLifecycleOwner
    }.run {
        root
    }

}