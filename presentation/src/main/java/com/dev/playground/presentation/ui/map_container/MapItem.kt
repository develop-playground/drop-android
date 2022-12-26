package com.dev.playground.presentation.ui.map_container

import com.naver.maps.geometry.LatLng
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng

data class MapItem(
    val position: LatLng,
    val image: String,
) : TedClusterItem {

    override fun getTedLatLng() = TedLatLng(position.latitude, position.longitude)

}