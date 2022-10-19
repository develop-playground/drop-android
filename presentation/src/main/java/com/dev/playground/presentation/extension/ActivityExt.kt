package com.dev.playground.presentation.extension

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

fun Activity.hasPermission(permissions: List<String>) = permissions.all {
    ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED
}

fun Activity.requestPermissions(permissions: List<String>, requestPermissionCode: Int) =
    ActivityCompat.requestPermissions(
        this,
        permissions.toTypedArray(),
        requestPermissionCode
    )