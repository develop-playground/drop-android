package com.dev.playground.presentation.ui.map_container

import android.view.View
import com.naver.maps.geometry.LatLng
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng
import java.lang.ref.WeakReference

data class DropClusterItem(
    val position: LatLng,
    val imageUrl: String?,
    val count: Int,
    val view: MarkerView,
) : TedClusterItem {

    override fun getTedLatLng() = TedLatLng(position.latitude, position.longitude)

    fun bind(count: Int = 0, onResourceReady: (View) -> Unit) {
        view.setClusterItem(
            item = this,
            count = count,
            onResourceReady = onResourceReady
        )
    }

}