package com.dev.playground.presentation.util

import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

fun Activity.hasPermission(vararg permissions: String): Boolean = permissions.all {
    ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}

fun Activity.requestPermission(vararg permissions: String, requestCode: Int) {
    ActivityCompat.requestPermissions(this, permissions, requestCode)
}

fun AppCompatActivity.repeatOnLifecycleState(
    state: Lifecycle.State = Lifecycle.State.STARTED,
    block: CoroutineScope.() -> Unit
) = lifecycleScope.launch {
    repeatOnLifecycle(state, block)
}