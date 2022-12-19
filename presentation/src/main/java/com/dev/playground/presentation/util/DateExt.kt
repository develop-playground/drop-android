package com.dev.playground.presentation.util

import java.text.SimpleDateFormat
import java.util.*

fun currentDate(): String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())