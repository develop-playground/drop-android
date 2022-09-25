package com.dev.playground.presentation.main

import android.os.Bundle
import android.widget.TextView
import com.dev.playground.presentation.R
import com.dev.playground.presentation.base.BaseActivity
import com.dev.playground.presentation.databinding.ActivityMainBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import ted.gun0912.clustering.naver.TedNaverClustering

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate),
    OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSyncMap()
        initViews()
    }

    private fun initViews() = with(binding) {

    }

    override fun onMapReady(p0: NaverMap): Unit = with(p0) {
        TedNaverClustering.with<MapItem>(this@MainActivity, this)
            .customMarker { clusterItem ->
                Marker(clusterItem.position).apply {
                    icon = MarkerIcons.RED
                    title = clusterItem.position.latitude.toString()
                }

            }
            .customCluster {
                TextView(this@MainActivity).apply {
                    setBackgroundColor(android.graphics.Color.GREEN)
                    setTextColor(android.graphics.Color.WHITE)
                    text = "${it.size}개"
                    setPadding(10, 10, 10, 10)
                }
            }
            .items(generateItems(this))
            .make()
    }

    private fun setSyncMap() {
        val fm = supportFragmentManager
        val mapFragment = fm.findFragmentById(R.id.mapMain) as? MapFragment
            ?: MapFragment.newInstance().also {
                fm.beginTransaction().add(R.id.mapMain, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun generateItems(map: NaverMap): ArrayList<MapItem> {
        return ArrayList<MapItem>().apply {
            repeat(50) {
                val bounds = map.contentBounds
                val temp = MapItem(
                    LatLng(
                        (bounds.northLatitude - bounds.southLatitude) * Math.random() + bounds.southLatitude,
                        (bounds.eastLongitude - bounds.westLongitude) * Math.random() + bounds.westLongitude
                    )
                )
                add(temp)
            }
        }
    }

}