package com.dev.playground.presentation.util

import android.content.Context
import android.content.Intent

inline fun <reified T> Context.startActivity(noinline action: Intent.() -> Unit) {
    startActivity(Intent(this, T::class.java).apply(action))
}