package com.dev.playground.presentation.util

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Activity.hasPermission(vararg permissions: String): Boolean = permissions.all {
    ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}

fun Activity.requestPermission(vararg permissions: String, requestCode: Int) {
    ActivityCompat.requestPermissions(this, permissions, requestCode)
}