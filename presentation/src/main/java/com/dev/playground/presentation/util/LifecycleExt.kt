package com.dev.playground.presentation.util

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun <T : Lifecycle.State> LifecycleOwner.lifecycleScope(
    state: T,
    block: suspend CoroutineScope.() -> Unit
): Unit {
    lifecycleScope.launch {
        repeatOnLifecycle(state, block)
    }
}