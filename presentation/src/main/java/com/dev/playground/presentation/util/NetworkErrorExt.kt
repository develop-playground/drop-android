package com.dev.playground.presentation.util

fun errorStatusCode(t: Throwable): String = t.message?.split(" ")?.get(1).toString()