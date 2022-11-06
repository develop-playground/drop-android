package com.dev.playground.presentation.util

import android.content.Context
import android.content.Intent
import android.widget.Toast

inline fun <reified T> Context.startActivity(noinline action: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply(action))
}

fun Context.showToast(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}