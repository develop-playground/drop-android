package com.dev.playground.presentation.model.base

import androidx.annotation.LayoutRes

abstract class SimpleUIModel(@LayoutRes val layoutRes: Int) : SimpleItemDiffCallback.DiffCallback