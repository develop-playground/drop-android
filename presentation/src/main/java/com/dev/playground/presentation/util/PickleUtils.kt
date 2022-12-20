package com.dev.playground.presentation.util

import android.content.Context
import android.media.ExifInterface
import android.provider.MediaStore
import com.charlezz.pickle.data.entity.Media
import java.io.File

private fun Media.getRealPath(context: Context): String? {
    return context.contentResolver.query(getUri(), null, null, null, null)?.use {
        it.moveToNext()
        it.getString(it.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA))
    }
}

fun Media.mapToFile(context: Context): File? = getRealPath(context)?.let { path -> File(path) }

fun List<Media>.findLatLng(context: Context): FloatArray? {
    forEach {
        val latLng = it.getLatLng(context)
        if (latLng != null) {
            return latLng
        }
    }
    return null
}

private fun Media.getLatLng(context: Context): FloatArray? {
    return try {
        getRealPath(context)?.let {
            val exif = ExifInterface(it)
            val latLng = FloatArray(2)

            if (exif.getLatLong(latLng)) {
                latLng
            } else {
                null
            }
        }
    } catch (e: Exception) {
        null
    }
}