package com.dev.playground.presentation.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Fragment.repeatOnLifecycleState(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: CoroutineScope.() -> Unit,
) = viewLifecycleOwner.lifecycleScope.launch {
    repeatOnLifecycle(state, block)
}