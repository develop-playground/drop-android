package com.dev.playground.presentation.main

import com.naver.maps.geometry.LatLng
import ted.gun0912.clustering.clustering.TedClusterItem
import ted.gun0912.clustering.geometry.TedLatLng

class MapItem(val position: LatLng) : TedClusterItem {

    override fun getTedLatLng() = TedLatLng(position.latitude, position.longitude)

}